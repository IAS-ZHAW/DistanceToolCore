/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.ops;

public class DivergenceOp2 implements Operation2 {
	@Override
	public double execute(double a, double b) {
		double n = (a-b);
		double m = (a+b);
		return (n*n)/(m*m);
	}
}
