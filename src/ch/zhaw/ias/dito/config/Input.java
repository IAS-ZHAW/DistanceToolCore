package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.jcip.annotations.NotThreadSafe;

@XmlAccessorType(XmlAccessType.NONE)
@NotThreadSafe
public final class Input { 
	@XmlElement	
	private String filename;
  @XmlElement 
  private String separator;	

  public Input() {
		filename = "";
		separator = ";";
	}
	
	 public Input(String filename, String separator) {
	     this.filename = filename;
	     this.separator = separator;
	 }
	
	public String getFilename() {
		return filename;
	}
	
	public char getSeparator() {
	  //very good idea to just access index 0
	  //shows that the engineer should be fired
	  //TODO think of something better
	  return separator.charAt(0);
	}
	
	@Override
	public boolean equals(Object obj) {
	  if (obj instanceof Input == false) {
	    return false;
	  }
	  Input i = (Input) obj;
	  return filename.equals(i.filename)&& separator.equals(i.separator); 
	}
	 
  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setSeparator(char separator) {
    this.separator = Character.toString(separator);
  }	
}