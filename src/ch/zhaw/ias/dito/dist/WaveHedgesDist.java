/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.WaveHedgesOp2;

public class WaveHedgesDist extends AbstractDist {
	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new WaveHedgesOp2());
		return dist.sum();
	}
}
