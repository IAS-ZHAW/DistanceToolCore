/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito;

public interface EigenvalueDecomposition {
  public double[] getSortedEigenvalues();
  public double[][] getReducedDimensions();
  public double[][] getReducedDimensions(int dimensions);
}
