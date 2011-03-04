package ch.zhaw.ias.dito.config;

import java.io.File;

import javax.xml.bind.annotation.XmlTransient;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public final class Input { 
	private File file;
  private String separator;	

  public Input() {
		file = new File("");
		separator = ";";
	}
	
  public Input(String file, String separator) {
    this(new File(file), separator);
  }
  
	 public Input(File file, String separator) {
	     this.file = file;
	     this.separator = separator;
	 }
	
	public String getFilename() {
		return file.getAbsolutePath();
	}
	
	public Character getSeparator() {
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
	  return file.equals(i.file)&& separator.equals(i.separator); 
	}

	@XmlTransient
	public File getFile() {
	  return file;
	}
	
  public void setFilename(String filename) {
    this.file = new File(filename);
  }

  public void setSeparator(Character separator) {
    this.separator = Character.toString(separator);
  }	
  
  
}