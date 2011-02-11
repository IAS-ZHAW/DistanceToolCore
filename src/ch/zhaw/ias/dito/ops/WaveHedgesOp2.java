package ch.zhaw.ias.dito.ops;

public class WaveHedgesOp2 implements Operation2 {
	public double execute(double a, double b) {
		return 1 - (Math.min(a, b)/Math.max(a, b));
	}
}
