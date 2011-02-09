package ch.zhaw.ias.dito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import ch.zhaw.ias.dito.dist.DistanceSpec;

public final class Matrix {
	private final DVector[] cols;
	
	public static Matrix readFromFile(File file) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(file), ';');
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Matrix == false) {
			return false;
		}
		Matrix m = (Matrix) obj;
		if (m.getRowCount() != getRowCount() || m.cols.length != cols.length) {
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
}
