package ch.zhaw.ias.dito.ops;

public class CanberraOp implements Operation {
	public double execute(double a, double b) {
		return Math.abs((a-b))/(a+b);
	}
}

