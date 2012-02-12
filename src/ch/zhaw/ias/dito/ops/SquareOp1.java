/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.ops;

public class SquareOp1 implements Operation1 {
  @Override
  public double execute(double a) {
    //don't use Math.pow() this is much faster
    return a * a;
  }
}
