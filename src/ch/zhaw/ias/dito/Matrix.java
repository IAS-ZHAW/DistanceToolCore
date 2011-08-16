package ch.zhaw.ias.dito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import net.jcip.annotations.Immutable;
import au.com.bytecode.opencsv.CSVWriter;
import ch.zhaw.ias.dito.config.ImportWrapper;
import ch.zhaw.ias.dito.config.Input;
import ch.zhaw.ias.dito.dist.DistanceSpec;
import ch.zhaw.ias.dito.util.Logger;
import ch.zhaw.ias.dito.util.Logger.LogLevel;

/**
 * In respect of possible concurrent calculation this class is immutable.
 * It's absolutely necessary that this class is immutable
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 * TODO A new symmetric attribute could improve performance in comparison functions.
 * The attribute could be automatically set on new matrixes after distance operations
 */
@Immutable
public final class Matrix {
	private final List<DVector> cols;
		
  public static ImportWrapper readFromFile(File file, char separator) throws IOException {
    return readFromFile(file, separator, false);
  }	
  
  public static ImportWrapper readFromFile(File file, char separator, boolean ignoreFirstLine) throws IOException {
    return readFromFile(new Input(file, separator, ignoreFirstLine, true, -1, -2, true, -1, -2));
  }
  
	/**
   * all whitespaces and all common masking symbols like " or ' will be removed at the beginning.
   * therefore texts are not allowed to contain the separator char
   * the first line will have line number 1
   * @param file
   * @param separator
   * @return
   * @throws IOException
   */
  public static ImportWrapper readFromFile(Input i) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(i.getFile()));
    Pattern pattern = Pattern.compile(Character.toString(i.getSeparator()));
    String line;
    List<String> columnNames = new ArrayList<String>();
    List<DVector> vectors = new ArrayList<DVector>();
    //read question titles
    if (i.isQuestionTitles() == true) {
      line = reader.readLine();
      Scanner s = new Scanner(line);
      s.useDelimiter(pattern);
      int rowCounter = 0;
      while (s.hasNext()) {
        rowCounter++;
        // ignore excluded columns
        if (i.isAllQuestions() == false && (rowCounter < i.getStartQuestion() || rowCounter > i.getEndQuestion())) {
          s.next();
          continue;
        }
        columnNames.add(s.next());
      }
    }
    int lineCounter = 0;
    while ((line = reader.readLine()) != null) {
      lineCounter++;
      //ignore certain lines if configured
      if (i.isAllSurveys() == false && (lineCounter < i.getStartSurvey() || lineCounter > i.getEndSurvey())) {
        continue;
      }
      Scanner s = new Scanner(line);
      s.useDelimiter(pattern);
      int rowCounter = 0;
      ArrayList<Double> values = new ArrayList<Double>();
      while (s.hasNext()) {
        rowCounter++;
        // ignore excluded columns
        if (i.isAllQuestions() == false && (rowCounter < i.getStartQuestion() || rowCounter > i.getEndQuestion())) {
          s.next();
          continue;
        }
        if (s.hasNextDouble()) {
          values.add(s.nextDouble());
        } else {
          //convert to Double.NaN  
          values.add(Double.NaN);    
          s.next();
        }
      }
      vectors.add(new DVector(values));
    }
    return new ImportWrapper(columnNames, new Matrix(vectors.toArray(new DVector[0])));
  }
  
  public static void writeToFile(Matrix dist, String outputFilename, char separator, int precision) throws IOException {
    Logger.INSTANCE.log("writing results to output-file: " + outputFilename, LogLevel.INFORMATION);
    long start = System.currentTimeMillis();
    FileWriter fw = new FileWriter(outputFilename);
    CSVWriter writer = new CSVWriter(fw, separator, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.NO_QUOTE_CHARACTER);
    
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(precision);
    nf.setGroupingUsed(false);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    for (int i = 0; i < dist.getColCount(); i++) {
      DVector col = dist.col(i);
      String[] line = new String[col.length()];
      for (int j = 0; j < col.length(); j++) {
        line[j] = nf.format(col.component(j));
      }
      writer.writeNext(line);
    }
    Logger.INSTANCE.log("writing output finished after " + (System.currentTimeMillis() - start) + " ms", LogLevel.INFORMATION);
    fw.close();
  }
	
  public Matrix(DVector... cols) {
    if (checkLengths(cols) == false) {
      throw new IllegalArgumentException("not all vectors have same length");
    }
    this.cols = Arrays.asList(cols);
  }
	
	public Matrix(ArrayList<DVector> cols) {
	  this.cols = (List<DVector>) cols.clone();
  }

  public static Matrix createDoubleMatrix(double[][] distances) {
		DVector[] cols = new DVector[distances.length];
		for (int i = 0; i < distances.length; i++) {
			cols[i] = new DVector(distances[i]);
		}
		return new Matrix(cols);
	}
	
	public int getRowCount() {
		if (cols.size() > 0) {
			return cols.get(0).length();
		} else {
			return 0;
		}
	}
	
	public int getColCount() {
		return cols.size();
	}	
	
	/**
	 * removes the means of this matrix in a column oriented way.
	 * for each column the mean is calculated and then removed from all values.
	 * @return
	 */
	public Matrix removeMeans() {
	  DVector[] vecs = new DVector[getColCount()];
	  for (int i = 0; i < getColCount(); i++) {
	    vecs[i] = col(i).removeMean();
	  }
	  return new Matrix(vecs);
	}
	
	/**
	 * calculates the covariances between all the columns in this matrix.
	 * the resulting matrix will have dimensions [getColCount() X getColCount()]
	 * @return
	 */
	public Matrix covariance() {
	  Matrix m = removeMeans();
	  
    double[][] covariances = new double[getColCount()][getColCount()];
    for (int i = 0; i < m.getColCount(); i++) {
      DVector v = m.col(i);
      for (int j = i; j < m.getColCount(); j++) {
        DVector w = m.col(j);
        double covariance = v.scalarProduct(w)/(w.length()-1);
        //covariances are symmetric
        covariances[i][j] = covariance;
        covariances[j][i] = covariance;
      }
    }
    return createDoubleMatrix(covariances);
	}
  
  public Matrix correlationCoeffs() {
    Matrix m = removeMeans();
    //calculate variance for each vector
    double[] variances = new double[getColCount()];
    for (int i = 0; i < m.getColCount(); i++) {
      DVector v = m.col(i);
      variances[i] = v.unscaledVariance();
    }
    double[][] coeffs = new double[getColCount()][getColCount()];
    for (int i = 0; i < m.getColCount(); i++) {
      DVector v = m.col(i);
      for (int j = i; j < m.getColCount(); j++) {
        DVector w = m.col(j);
        double div = Math.sqrt(variances[i] * variances[j]);
        double coeff = v.scalarProduct(w) / div;
        //correlation coefficient is symmetric
        coeffs[i][j] = coeff;
        coeffs[j][i] = coeff;
      }
    }
    return createDoubleMatrix(coeffs);
  }
	

  /**
   * Returns true if all of the passed vectors have the same number of values/the same length. 
   * @param cols
   * @return
   */
	public static boolean checkLengths(DVector... cols) {
		int length = 0;
		for (int i = 0; i < cols.length; i++) {
		  DVector v = cols[i];
			if (length == 0) {
				length = v.length();
			}
			if (length != v.length()) {
				return false;
			}
			length = v.length();
		}	
		return true;
	}
	
	public DVector col(int index) {
		return cols.get(index);
	}
	
	public DVector row(int index) {
		if (index > getRowCount()) {
			throw new IndexOutOfBoundsException();
		}
		double[] values = new double[cols.size()];
		for (int i = 0; i < cols.size(); i++) {
		  DVector v = cols.get(i);
			values[i] = v.component(index);
		}
		return new DVector(values);
	}
	
	public Matrix calculateDistance(DistanceSpec dist) {
		double[][] distances = new double[getColCount()][getColCount()];
    for (int i = 0; i < getColCount(); i++) {
      DVector v = col(i);
    	for (int j = i; j < getColCount(); j++) {
    	  DVector w = col(j);
    		double distance = dist.distance(v, w);
    		//Map NaN Values to 0
    		if (Double.isNaN(distance)) {
    		  distance = 0;
    		}
    		distances[i][j] = distance;
    		distances[j][i] = distance;
    	}
    }
    return createDoubleMatrix(distances);
	}
	
  public Matrix calculateDistanceParallel(DistanceSpec dist, int numberOfThreads) {
    ExecutorService es = Executors.newFixedThreadPool(5);//newCachedThreadPool();
    double[][] distances = new double[getColCount()][getColCount()];
    for (int i = 0; i < getColCount(); i++) {
      //DVector v = helper.col(i);
      //this is not save: the distances-array could be modified in parallel and introduce race-conditions
      es.execute(new CalcRow(this, distances, dist, i));
    }
    es.shutdown();
    try {
      es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {

    }
    return createDoubleMatrix(distances);
  }	
	
  static final class CalcRow implements Runnable {
    private Matrix helper;
    private double[][] distances;
    private DistanceSpec dist;
    private int startAt;
    
    CalcRow(Matrix helper, double[][] distances, DistanceSpec dist, int startAt) {
      this.helper = helper;
      this.distances = distances;
      this.dist = dist;//(DistanceSpec) dist.clone();
      this.startAt = startAt;
    }
    
    @Override
    public void run() {
      DVector v = helper.col(startAt);
      for (int j = startAt; j < helper.getColCount(); j++) {
        DVector w = helper.col(j);
        double distance = dist.distance(v, w);
        //Map NaN Values to 0
        if (Double.isNaN(distance)) {
          distance = 0;
        }
        distances[startAt][j] = distance;
        distances[j][startAt] = distance;
      }
    }
  }
  
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matrix == false) {
			return false;
		}
		Matrix m = (Matrix) obj;
    if (equalDimensions(m) == false) {
      return false;
    }
		for (int i = 0; i < cols.size(); i++) {
			if (m.col(i).equals(col(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public Matrix transpose() {
		ArrayList<DVector> misterD = new ArrayList<DVector>(); 
		for (int i = 0; i < getRowCount(); i++) {
		  misterD.add(row(i));
		}
		return new Matrix(misterD);
	}
	
	@Override
	public String toString() {
	  StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < cols.size(); i++) {
			sb.append(cols.get(i).toString());
			if (i+1 < cols.size()) {
				sb.append(',');
			}
		}
		return sb.append(')').toString();
	}

	public boolean isSquare() {
		return getColCount() == getRowCount();
	}
	
	public boolean equalsRounded(Matrix b, int precision) {
	  if (equalDimensions(b) == false) {
	    return false;
	  }
	  NumberFormat nf = NumberFormat.getNumberInstance();
	  nf.setMaximumFractionDigits(precision);
	  nf.setGroupingUsed(false);
	  nf.setRoundingMode(RoundingMode.HALF_UP);
    for (int i = 0; i < getColCount(); i++) {
      DVector col = col(i);
      DVector colB = b.col(i);
      for (int j = 0; j < col.length(); j++) {
        String s = nf.format(col.component(j));
        String sB = nf.format(colB.component(j));
        if (s.equals(sB) == false) {
          return false;
        }
      }
    }
    return true;
	}
	
	/**
	 * Checks wether matrix m and this matrix have the same amount of rows and columns.
	 * @param m
	 * @return
	 */
	public boolean equalDimensions(Matrix m) {
	   return (m.getRowCount() == getRowCount() && m.getColCount() == getColCount());
	}
	
  public Matrix autoRescale() {
    DVector[] rescaled = new DVector[getColCount()];
    for (int i = 0; i < getColCount(); i++) {
      DVector v = col(i);
      rescaled[i] = v.autoRescale();
    }
    return new Matrix(rescaled);
  }
  
  public Matrix toBinary() {
    DVector[] binary = new DVector[getColCount()];
    for (int i = 0; i < getColCount(); i++) {
      DVector v = col(i);
      binary[i] = v.toBinary();
    }
    return new Matrix(binary);
  }
  
  /**
   * Returns the minimum or the maximum of this matrix
   * @param max if set to true the maximum will be returned, otherwise the minimums will be returned
   * @return
   */
  public double extremum(boolean max) {
    double extremum;
    if (max) {
      extremum = Double.MIN_VALUE;
    } else {
      extremum = Double.MAX_VALUE;
    }
    for (int i = 0; i < getColCount(); i++) {
      DVector v = col(i);
      double value;
      if (max) {
        value = v.max();
        extremum = Math.max(extremum, value);
      } else {
        value = v.min();
        extremum = Math.min(extremum, value);
      }
    }
    return extremum;
  }

  /**
   * The resulting matrix will be a transposed Jama-style copy of this matrix.
   * @return
   */
  public Jama.Matrix toJama() {
    double[][] values = new double[getColCount()][getRowCount()];
    for (int i = 0; i < getColCount(); i++) {
      DVector v = col(i);
      values[i] = v.getValues();
    }
    return new Jama.Matrix(values);
  }

  /**
   * the method ensures that every vector will only be included once into the random sample.
   * if the specified sampleSize is bigger than this matrix the matrix will return a reference to itself 
   * @param sampleSize
   * @return
   */
  public Matrix getRandomSample(int sampleSize) {
    int totalSize = getColCount();
    if (sampleSize >= totalSize) {
      //no need to create a new matrix. return this pointer
      return this;
    }
    Random rand = new Random();
    
    //use a hash set to ensure that no duplicates will be included
    Set<Integer> indexes = new HashSet<Integer>(sampleSize);
    /* 
     * first a list of indexes is calculated. this is done because if the elements are added directly to a list of DVectors
     * this could lead to errors. this matrix could include duplicated DVectors. duplicated DVectors can only be added once to a
     * Set. therefore if added directly this could (theoretically) lead to an endless loop, because the sample size could never be reached.
     * i.e. if matrix size is 500 and sample size is 499 and 3 DVectors are duplicates.
     */
    while (indexes.size() < sampleSize) {
      indexes.add(rand.nextInt(totalSize));
    }
    DVector[] vecs = new DVector[sampleSize];
    int i = 0;
    for (Integer index : indexes) {
      vecs[i] = col(index);
      i++;
    }
    return new Matrix(vecs);
  }
  
  public boolean containsSpecial() {
    for (int i = 0; i < getColCount(); i++) {
      DVector v = col(i);
      if (v.containsSpecial()) {
        return true;
      }
    }
    return false;
  }
}
