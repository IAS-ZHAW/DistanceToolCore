/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.util;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.ias.dito.util.Logger.LogLevel;

public class Logger {
  public enum LogLevel {
    TRACE, WARNING, INFORMATION, ERROR, FATAL_ERROR;
  }
  
  public static Logger INSTANCE = new Logger();
  private List<LogInterface> interfaces = new ArrayList<LogInterface>();
  
  public void log(String text, LogLevel level) {
    for (LogInterface face : interfaces) {
      face.log(text, level);
    }
  }
  
  public void addInterface(LogInterface li) {
    interfaces.add(li);
  }
  
  public void removeInterface(LogInterface li) {
    interfaces.remove(li);
  }

  public void log(String text, LogLevel level, boolean start) {
    for (LogInterface face : interfaces) {
      face.log(text, level, start);
    }
  }
  
  public void error(String text, Throwable t) {
    for (LogInterface face : interfaces) {
      face.error(text, t);
    }
  }
}
