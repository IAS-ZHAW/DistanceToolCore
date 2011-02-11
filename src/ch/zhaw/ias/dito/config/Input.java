package ch.zhaw.ias.dito.config;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Input { 
	@XmlElement	
	private final String filename;
  @XmlElement 
  private final String separator;	
 
	private Input() {
		filename = null;
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
}