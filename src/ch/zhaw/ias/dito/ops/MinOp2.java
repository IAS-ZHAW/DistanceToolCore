package ch.zhaw.ias.dito.ops;

public class MinOp2 implements Operation2 {
	@Override
	public double execute(double a, double b) {
		return Math.min(a, b);
	}
}
