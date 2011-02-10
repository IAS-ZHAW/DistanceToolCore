package ch.zhaw.ias.dito.config;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Output { 
	@XmlElement	
	private final String filename;
 
	private Output() {
		filename = null;
	}
	
	 public Output(String filename) {
	     this.filename = filename;
	 }
	
	public String getFilename() {
		return filename;
	}
}