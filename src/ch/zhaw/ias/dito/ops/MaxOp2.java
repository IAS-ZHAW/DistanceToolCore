package ch.zhaw.ias.dito.ops;

public class MaxOp2 implements Operation2 {
	@Override
	public double execute(double a, double b) {
		return Math.max(a, b);
	}
}
