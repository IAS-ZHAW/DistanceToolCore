/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.ops;

public class MultiplyOp1  implements Operation1 {
  private double multiplier;
  
  public MultiplyOp1(double multiplier) {
    this.multiplier = multiplier;
  }
  
  @Override
  public double execute(double a) {
    return multiplier * a;
  }
}
