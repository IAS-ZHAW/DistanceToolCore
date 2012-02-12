/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;

/**
 * It is an essential requirement that all implementation classes are threadsafe.  
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 */
public interface DistanceSpec {
	
  /**
   * Compares two vectors and calculates the distance between the two.
   * The method musn't execute any scaling or weighting on the vectors. 
   * @param a
   * @param b
   * @return Distance between the two vectors. In case both vectors are zero-vectors the function is allowed to return NaN.
   * NaN values will be mapped to a zero distance
   */
	public double distance(DVector a, DVector b);
	
	public Object clone();
}
