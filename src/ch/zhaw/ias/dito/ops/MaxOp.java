package ch.zhaw.ias.dito.ops;

public class MaxOp implements Operation {
	@Override
	public double execute(double a, double b) {
		return Math.max(a, b);
	}
}
