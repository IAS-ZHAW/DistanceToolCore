package ch.zhaw.ias.dito.config;

public enum ConfigProperty {
  INPUT_FILENAME("s1.lb.file"),
  INPUT_SEPARATOR("s1.lb.separator"),
  METHOD_NAME("s3.lb.method"),
  OUTPUT_FILENAME("s4.lb.file"),
  OUTPUT_SEPARATOR("s4.lb.separator"),
  OUTPUT_PRECISION("s4.lb.precision");
  
  private String key;
  
  private ConfigProperty(String key) {
    this.key = key;
  }
  
  public String getKey() {
    return key;
  }
}
