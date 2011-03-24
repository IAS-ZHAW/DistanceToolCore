package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlType;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
@XmlType(propOrder={"enableScale", "enableAutoScale" , "enableQuestionWeight", "enableDistanceWeight"})
public final class QuestionConfig {
	private boolean enableScale;
	private boolean enableQuestionWeight;
	private boolean enableDistanceWeight;
	private boolean enableAutoScale;
	
  public QuestionConfig() {
    this.enableScale = true;
    this.enableQuestionWeight = true;
    this.enableDistanceWeight = false;
    this.enableAutoScale = true;
  }

  public QuestionConfig(boolean enableScale, boolean enableQuestionWeight,
      boolean enableDistanceWeight, boolean enableAutoScale) {
    this.enableScale = enableScale;
    this.enableQuestionWeight = enableQuestionWeight;
    this.enableDistanceWeight = enableDistanceWeight;
    this.enableAutoScale = enableAutoScale;
  }

  public boolean isEnableScale() {
    return enableScale;
  }

  public boolean isEnableQuestionWeight() {
    return enableQuestionWeight;
  }

  public boolean isEnableDistanceWeight() {
    return enableDistanceWeight;
  }

  public boolean isEnableAutoScale() {
    return enableAutoScale;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof QuestionConfig == false) {
      return false;
    }
    QuestionConfig qc = (QuestionConfig) obj;
    return enableScale == qc.enableScale 
      && enableQuestionWeight == qc.enableQuestionWeight
      && enableDistanceWeight == qc.enableDistanceWeight
      && enableAutoScale == qc.enableAutoScale;
  }
 
  public void setEnableScale(boolean enableScale) {
    this.enableScale = enableScale;
  }
  
  public void setEnableAutoScale(boolean enableAutoScale) {
    this.enableAutoScale = enableAutoScale;
  }
  
  public void setEnableDistanceWeight(boolean enableDistanceWeight) {
    this.enableDistanceWeight = enableDistanceWeight;
  }
  
  public void setEnableQuestionWeight(boolean enableQuestionWeight) {
    this.enableQuestionWeight = enableQuestionWeight;
  }
}
