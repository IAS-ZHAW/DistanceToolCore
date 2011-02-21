package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlElement;

public class Question {
  @XmlElement
	private final int column;
  @XmlElement 
	private final String name;
  @XmlElement 
	private final Double scaling;
  @XmlElement 
	private final Double questionWeight;
  @XmlElement 
	private final Double distanceWeight;
	
  public Question() {
    this.column = -1;
    this.name = "";
    this.scaling = 1.0;
    this.questionWeight = 1.0;
    this.distanceWeight = 1.0;
  }
	
	public Question(Integer column, String name, Double scaling, Double questionWeight,
      Double distanceWeight) {
    this.column = column;
    this.name = name;
    this.scaling = scaling;
    this.questionWeight = questionWeight;
    this.distanceWeight = distanceWeight;
  }
  
  public int getColumn() {
    return column;
  }
  
  public String getName() {
    return name;
  }
  
  public Double getScaling() {
    return scaling;
  }
  
  public Double getQuestionWeight() {
    return questionWeight;
  }
  
  public Double getDistanceWeight() {
    return distanceWeight;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Question == false) {
      return false;
    }
    Question q = (Question) obj;
    return column == q.column 
      && name.equals(q.name) 
      && scaling.equals(q.scaling)
      && questionWeight.equals(q.questionWeight)
      && distanceWeight.equals(q.distanceWeight);
  }
}
