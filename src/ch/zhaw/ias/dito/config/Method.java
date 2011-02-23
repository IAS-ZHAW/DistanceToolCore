package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlElement;

import ch.zhaw.ias.dito.dist.DistanceMethodEnum;

public class Method {
  @XmlElement 
  private String name;
  //private DistanceMethodEnum method; 
  
  public Method() {
    //TODO this is not a very good idea
    name="";//method = DistanceMethodEnum.get("Euklid");
  }
  
  public Method(String name) {
    this.name = name;//this.method = DistanceMethodEnum.get(name);;
  }
  
  public String getName() {
    return name;//method.getName();
  }
  
  /*public void setName(String name) {
    this.name = name;//method = DistanceMethodEnum.get(name);
  }*/
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Method == false) {
      return false;
    }
    Method m = (Method) obj;
    return name.equals(m.name);//method == m.method;
  }
}
