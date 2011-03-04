package ch.zhaw.ias.dito;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.Immutable;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import ch.zhaw.ias.dito.dist.DistanceSpec;

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
	
	public static Matrix readFromFile(File file, char separator) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(file), separator);
		String [] nextLine;
		List<DVector> vectors = new ArrayList<DVector>();
	    while ((nextLine = reader.readNext()) != null) {
	    	double[] values = new double[nextLine.length];
	        for (int i = 0; i < values.length; i++) {
	        	if (nextLine[i].length() == 0 || nextLine[i].isEmpty() == true || " ".equals(nextLine[i])) {
	        		values[i] = Float.NaN;
	        	} else {
	        		values[i] = Double.parseDouble(nextLine[i]);
	        	}
	        }
	        vectors.add(new DVector(values));
	    }
	    return new Matrix(vectors.toArray(new DVector[0]));
	}
	
  public static void writeToFile(Matrix dist, String outputFilename, char separator, int precision) throws IOException {
    System.out.println("writing results to output-file: " + outputFilename);
    long start = System.currentTimeMillis();
    FileWriter fw = new FileWriter(outputFilename);
    //BufferedWriter bw = new BufferedWriter(fw, 8192);
    CSVWriter writer = new CSVWriter(fw, separator, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.NO_QUOTE_CHARACTER);
    
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(precision);
    nf.setGroupingUsed(false);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    for (int i = 0; i < dist.getColCount(); i++) {
      DVector col = dist.col(i);
      //StringBuffer sb = new StringBuffer(col.length()*10);
      String[] line = new String[col.length()];
      for (int j = 0; j < col.length(); j++) {
        line[j] = nf.format(col.component(j));
        //sb.append(nf.format(col.component(j)));
        //nf.format();
        //bw.write(Double.toString(col.component(j)));
      }
      //sb.append("\r\n");
      //bw.write("\r\n");
      writer.writeNext(line);
    }
    System.out.println("writing output finished after " + (System.currentTimeMillis() - start) + " ms");
    //bw.close();
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
	
	public Matrix calculateDistance(DistanceSpec dist, boolean isBinary) {
		double[][] distances = new double[getColCount()][getColCount()];
	    for (int i = 0; i < getColCount(); i++) {
	      DVector v = col(i);
	    	for (int j = i; j < getColCount(); j++) {
	    	  DVector w = col(j);
	    		double distance = dist.distance(v, w);
	    		distances[i][j] = distance;
	    		distances[j][i] = distance;
	    	}
	    }
	    return createDoubleMatrix(distances);
	}
	
  /*public Matrix calculateDistance(DistanceSpec dist, boolean isBinary) {
    ExecutorService es = Executors.newFixedThreadPool(5);//CachedThreadPool();
    double[][] distances = new double[getColCount()][getColCount()];
    for (int i = 0; i < getColCount(); i++) {
      //DVector v = helper.col(i);
      es.execute(new CalcRow(this, distances, dist, i));
    }
    es.shutdown();
    try {
      es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {

    }
    return createDoubleMatrix(distances);
  }	
	
  class CalcRow implements Runnable {
    private Matrix helper;
    private double[][] distances;
    private DistanceSpec dist;
    private int startAt;
    
    CalcRow(Matrix helper, double[][] distances, DistanceSpec dist, int startAt) {
      this.helper = helper;
      this.distances = distances;
      this.dist = (DistanceSpec) dist.clone();
      this.startAt = startAt;
    }
    
    @Override
    public void run() {
      DVector v = helper.col(startAt);
      for (int j = startAt; j < helper.getColCount(); j++) {
        DVector w = helper.col(j);
        double distance = dist.distance(v, w);
        distances[startAt][j] = distance;
        distances[j][startAt] = distance;
      }
    }
  }*/
  
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
}
