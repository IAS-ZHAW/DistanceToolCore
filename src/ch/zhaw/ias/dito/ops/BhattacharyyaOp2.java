package ch.zhaw.ias.dito.ops;

public class BhattacharyyaOp2 implements Operation2 {
	public double execute(double a, double b) {
		double d = Math.sqrt(a)-Math.sqrt(b);
		return d*d; //Don't use Math.pow. It is much slower.
	}
}
