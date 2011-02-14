package ch.zhaw.ias.dito.ops;

public class DivergenceOp2 implements Operation2 {
	@Override
	public double execute(double a, double b) {
		double n = (a-b);
		double m = (a+b);
		return (n*n)/(m*m);
	}
}
