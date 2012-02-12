/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.config;

import java.util.ArrayList;
import java.util.List;

public class PropertyGuardian {
  //private Map<ConfigProperty, PropertyDistributor> properties = new HashMap<ConfigProperty, PropertyDistributor>();
  private List<PropertyListener> listener = new ArrayList<PropertyListener>();
  
  public void propertyChanged(ConfigProperty prop) {
    for (PropertyListener l : listener) {
      if (l.listensTo(prop)) {
        l.propertyChanged(prop);
      }
    }
  }
  
  public void addListener(PropertyListener listener) {
    this.listener.add(listener);
  }
  
  public void removeListener(PropertyListener listener) {
    this.listener.remove(listener);
  }
}
