/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.ops;

/**
 * A simple interface for all operations with 1 operands (unary operations)
 * All implementing classes must be threadsafe.
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 */
public interface Operation1 {
	public double execute(double a);
}
