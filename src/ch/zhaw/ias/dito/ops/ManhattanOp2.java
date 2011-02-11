package ch.zhaw.ias.dito.ops;

public class ManhattanOp2 implements Operation2 {
	public double execute(double a, double b) {
		return Math.abs((a-b));
	}
}

