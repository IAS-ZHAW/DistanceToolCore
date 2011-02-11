package ch.zhaw.ias.dito.config;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Output { 
	@XmlElement	
	private final String filename;
  @XmlElement 
  private final String separator;
  @XmlElement 
  private final Integer precision;
	 
	private Output() {
		filename = null;
		separator = ";";
		precision = 2;
	}
	
	 public Output(String filename, String separator, Integer precision) {
	     this.filename = filename;
	     this.separator = separator;
	     this.precision = precision;
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

  public int getPrecision() {
    return precision;
  }
}