package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;

public interface DistanceSpec {
	public double distance(double a, double b);
	
	public double distance(double a[], double b[]);
	
	public double distance(DVector a, DVector b);
	
	public Object clone();
}
