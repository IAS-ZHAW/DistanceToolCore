/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.DivergenceOp2;

/**
 * parameter p will be the number of effective comparisons executed.  
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 */
public class DivergenceDist extends AbstractDist {
  public double distance(DVector a, DVector b) {
    DVector dist = a.zipWith(b, new DivergenceOp2());
    return dist.sum()/dist.length();
  } 
}
