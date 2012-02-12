/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.dist;

public abstract class AbstractDist implements DistanceSpec, Cloneable {
		
	@Override
	public Object clone() {
	  try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error();
    }
	}
}
