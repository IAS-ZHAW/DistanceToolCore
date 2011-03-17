package ch.zhaw.ias.dito.config;

import net.jcip.annotations.Immutable;

@Immutable
public enum ConfigProperty {
  INPUT_FILENAME("s1.lb.file"),
  INPUT_SEPARATOR("s1.lb.separator"),
  METHOD_NAME("s3.lb.method"),
  OUTPUT_FILENAME("s5.lb.file"),
  OUTPUT_SEPARATOR("s5.lb.separator"),
  OUTPUT_PRECISION("s5.lb.precision"),
  QUESTION_NUMBER("misc.lb.questionNumber"),
  INPUT_SIZE("misc.lb.inputSize");
  
  private final String key;
  
  private ConfigProperty(String key) {
    this.key = key;
  }
  
  public String getKey() {
    return key;
  }
}
