package ch.zhaw.ias.dito.config;

import javax.xml.bind.annotation.XmlElement;

public class Question {
  @XmlElement
	private final int id;
  @XmlElement 
	private final String name;
  @XmlElement 
	private final Double scaling;
  @XmlElement 
	private final Double questionWeight;
  @XmlElement 
	private final Double distanceWeight;
	
  public Question() {
    this.id = -1;
    this.name = "";
    this.scaling = 1.0;
    this.questionWeight = 1.0;
    this.distanceWeight = 1.0;
  }
	
	public Question(Integer id, String name, Double scaling, Double questionWeight,
      Double distanceWeight) {
    this.id = id;
    this.name = name;
    this.scaling = scaling;
    this.questionWeight = questionWeight;
    this.distanceWeight = distanceWeight;
  }
  
  public int getId() {
    return id;
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
}
