package ch.zhaw.ias.dito;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import au.com.bytecode.opencsv.CSVReader;

import ch.zhaw.ias.dito.dist.DistanceSpec;


/**
 * A new symmetric attribute could improve performance in comparison functions.
 * The attribute could be automatically set on new matrixes after distance operations
 * @author Thomas
 *
 */
public final class Matrix {
	private final DVector[] cols;
	
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
	
	public Matrix(DVector... cols) {
		if (checkLengths(cols) == false) {
			throw new IllegalArgumentException("not all vectors have same length");
		}
		this.cols = cols;
	}
	
	public Matrix(double[][] distances) {
		cols = new DVector[distances.length];
		for (int i = 0; i < distances.length; i++) {
			cols[i] = new DVector(distances[i]);
		}
	}
	
	public int getRowCount() {
		if (cols.length > 0) {
			return cols[0].length();
		} else {
			return 0;
		}
	}
	
	public int getColCount() {
		return cols.length;
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
		return cols[index];
	}
	
	public DVector row(int index) {
		if (index > getRowCount()) {
			throw new IndexOutOfBoundsException();
		}
		double[] values = new double[cols.length];
		for (int i = 0; i < cols.length; i++) {
			DVector v = cols[i];
			values[i] = v.component(index);
		}
		return new DVector(values);
	}
	
	public Matrix calculateDistance(DistanceSpec dist) {
		double[][] distances = new double[getRowCount()][getRowCount()];
		Matrix helper = this.transpose();
	    for (int i = 0; i < helper.getColCount(); i++) {
	    	DVector v = helper.col(i);
	    	for (int j = i; j < helper.getColCount(); j++) {
	    		DVector w = helper.col(j);
	    		double distance = dist.distance(v, w);
	    		distances[i][j] = distance;
	    		distances[j][i] = distance;
	    	}
	    }
	    return new Matrix(distances);
	}
	
  /*public Matrix calculateDistance(DistanceSpec dist) {
    ExecutorService es = Executors.newCachedThreadPool();
    double[][] distances = new double[getRowCount()][getRowCount()];
    Matrix helper = this.transpose();
    for (int i = 0; i < helper.getColCount(); i++) {
      //DVector v = helper.col(i);
      es.execute(new CalcRow(helper, distances, dist, i));
    }
    es.shutdown();
    try {
      es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {

    }
    return new Matrix(distances);
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
		for (int i = 0; i < cols.length; i++) {
			if (m.col(i).equals(col(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public Matrix transpose() {
		double[][] values = new double[getRowCount()][cols.length];
		for (int i = 0; i < cols.length; i++) {
			DVector v = cols[i];
			for (int j = 0; j < v.length(); j++) {
				values[j][i] = v.component(j); 
			}
		}
		return new Matrix(values);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < cols.length; i++) {
			sb.append(cols[i].toString());
			if (i+1 < cols.length) {
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
}
