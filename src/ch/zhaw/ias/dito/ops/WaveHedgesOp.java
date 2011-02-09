package ch.zhaw.ias.dito.ops;

public class WaveHedgesOp implements Operation {
	public double execute(double a, double b) {
		return 1 - (Math.min(a, b)/Math.max(a, b));
	}
}
