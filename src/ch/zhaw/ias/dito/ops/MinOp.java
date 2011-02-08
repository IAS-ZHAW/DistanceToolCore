package ch.zhaw.ias.dito.ops;

public class MinOp implements Operation {
	@Override
	public double execute(double a, double b) {
		return Math.min(a, b);
	}
}
