package ch.zhaw.ias.dito.ops;

public class EuklidOp implements Operation {
	public double execute(double a, double b) {
		double d = (a-b);
		return d*d; //Don't use Math.pow. It is much slower.
	}
}
