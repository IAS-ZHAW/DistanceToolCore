/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.ops;

public class BhattacharyyaOp2 implements Operation2 {
	public double execute(double a, double b) {
		double d = Math.sqrt(a)-Math.sqrt(b);
		return d*d; //Don't use Math.pow. It is much slower.
	}
}
