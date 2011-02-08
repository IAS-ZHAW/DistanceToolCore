package ch.zhaw.ias.dito;

import java.util.Arrays;

import ch.zhaw.ias.dito.ops.AddOp;
import ch.zhaw.ias.dito.ops.MaxOp;
import ch.zhaw.ias.dito.ops.MinOp;
import ch.zhaw.ias.dito.ops.Operation;

public final class DVector {
	private final double[] values;
	
	public DVector(double... values) {
		this.values = values;
	}
	
	public double sum() {
		return foldl(new AddOp());
	}
	
	public double max() {
		return foldl(new MaxOp());
	}
	
	public double min() {
		return foldl(new MinOp());
	}	
	
	public DVector add(DVector v) {
		return zipWith(v, new AddOp());
	}
	
	public int length() {
		return values.length;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DVector == false) {
			return false;
		}
		DVector v = (DVector) obj;
		//probably not necessary since values.equals will check anyway
		if (v.length() != this.length()) {
			return false;
		}
		return Arrays.equals(values, v.values);
	}

	public double component(int index) {
		return values[index];
	}
	
	public DVector zipWith(DVector v, Operation o) {
		if (v.values.length != values.length) {
			throw new IllegalArgumentException();
		}
		double[] c = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			c[i] = o.execute(values[i], v.values[i]);
		}
		return new DVector(c);
	}
	
	public double foldl(Operation op) {
		double result;
		if (values.length > 0) {
			result = values[0];
		} else {
			return 0;
		}
		for (int i = 1; i < values.length; i++) {
			result = op.execute(result, values[i]);
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("(");
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			if (i+1 < values.length) {
				sb.append(',');
			}
		}
		return sb.append(')').toString();
	}
}
