/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;

public abstract class AbstractBinaryDist implements DistanceSpec, Cloneable {
  @Override
  public double distance(DVector aVec, DVector bVec) {
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;
    for (int i = 0; i < aVec.length(); i++) {
      double vA = aVec.component(i);
      double vB = bVec.component(i);
      if (Double.isNaN(vA) || Double.isNaN(vB)) {
        continue;
      } else if (vA == 1.0 && vB == 1.0) {
        a++;
      } else if (vA == 1.0 && vB == 0.0) {
        b++;
      } else if (vA == 0.0 && vB == 1.0) {
        c++;
      } else if (vA == 0.0 && vB == 0.0) {
        d++;
      } else {
        throw new IllegalArgumentException("value " + vA + " or " + vB + " is not allowed");
      }
    }
    return distance(a, b, c, d);
  }
  
  /**
   * All parameters could be passed as integer, however double was chosen because this ensures
   * that all calculations are executed using double precision.
   * @param a Number of Components where aVec==1 and bVec==1
   * @param b Number of Components where aVec==1 and bVec==0
   * @param c Number of Components where aVec==0 and bVec==1
   * @param d Number of Components where aVec==0 and bVec==0
   * @return
   */
  public abstract double distance(double a, double b, double c, double d);
  
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error();
    }
  }
}
