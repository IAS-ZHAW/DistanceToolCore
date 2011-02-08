package ch.zhaw.ias.dito.ops;

public class AddOp implements Operation {
	@Override
	public double execute(double a, double b) {
		return a + b;
	}
}
