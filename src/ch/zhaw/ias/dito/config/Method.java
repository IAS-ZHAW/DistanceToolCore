package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlElement;

public class Method {
  @XmlElement 
  private final String name; 
  
  public Method() {
    name = "";
  }
  
  public Method(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Method == false) {
      return false;
    }
    Method m = (Method) obj;
    return name.equals(m.name);
  }
}
