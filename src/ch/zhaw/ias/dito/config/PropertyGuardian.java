package ch.zhaw.ias.dito.config;

import java.util.ArrayList;
import java.util.List;

public class PropertyGuardian {
  //private Map<ConfigProperty, PropertyDistributor> properties = new HashMap<ConfigProperty, PropertyDistributor>();
  private List<PropertyListener> listener = new ArrayList<PropertyListener>();
  
  public void propertyChanged(ConfigProperty prop, Object oldValue, Object newValue) {
    for (PropertyListener l : listener) {
      if (l.listensTo(prop)) {
        l.propertyChanged(prop, oldValue, newValue);
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
