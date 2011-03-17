package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;

public interface DistanceSpec {
	
  /**
   * Compares two vectors and calculates the distance between the two.
   * The method musn't execute any scaling or weighting on the vectors. 
   * @param a
   * @param b
   * @return Distance between the two vectors. In case both vectors are zero-vectors the function is allowed to return NaN.
   * NaN values will be mapped to 0
   */
	public double distance(DVector a, DVector b);
	
	public Object clone();
}
