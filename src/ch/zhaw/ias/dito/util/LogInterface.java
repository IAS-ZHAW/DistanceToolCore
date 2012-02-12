/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.util;

import ch.zhaw.ias.dito.util.Logger.LogLevel;

public interface LogInterface {
  public void log(String text, LogLevel level);
  public void log(String text, LogLevel level, boolean startProcess);
  public void error(String text, Throwable t);
}
