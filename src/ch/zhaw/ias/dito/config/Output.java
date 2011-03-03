package ch.zhaw.ias.dito.config;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public final class Output {
	private String filename;
  private String separator;
  private Integer precision;
	 
	public Output() {
		filename = "";
		separator = ";";
		precision = 2;
	}
	
	 public Output(String filename, String separator, Integer precision) {
     this.filename = filename;
     this.separator = separator;
     this.precision = precision;
	 }
	
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Output == false) {
      return false;
    }
    Output o = (Output) obj;
    return filename.equals(o.filename) && separator.equals(o.separator) && precision.equals(o.precision); 
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

  public Integer getPrecision() {
    return precision;
  }
  
  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setSeparator(char separator) {
    this.separator = Character.toString(separator);
  }

  public void setPrecision(Integer precision) {
    this.precision = precision;
  }
}