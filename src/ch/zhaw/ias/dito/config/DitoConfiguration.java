package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement( namespace = "http://ias.zhaw.ch/" )
public class DitoConfiguration { 
	@XmlElement	
  private final Input input;
	@XmlElement  
	private final Output output; 
  @XmlElement  
  private final Method method;
	
  private DitoConfiguration() {
	  input = null;
	  output = null;
	  method = null;
  }  
  
  private DitoConfiguration(Input input, Output output, Method method) {
	  this.input = input;
	  this.output = output;
	  this.method = method;
  }  
  
  public Input getInput() { 
    return input; 
  } 
  
  public Output getOutput() {
    return output;
  }
  
  public Method getMethod() {
    return method;
  }
}