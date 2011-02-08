package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;

public abstract class AbstractDist implements DistanceSpec {
	@Override
	public double distance(double a, double b) {
		return distance(new DVector(a), new DVector(b));
	}
	
	@Override
	public double distance(double[] a, double[] b) {
		return distance(new DVector(a), new DVector(b));
	}
}
