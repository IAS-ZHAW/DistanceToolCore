package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlElement;

public class Method {
  @XmlElement 
  private final String name; 
  
  public Method() {
    name = null;
  }
  
  public Method(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
}
