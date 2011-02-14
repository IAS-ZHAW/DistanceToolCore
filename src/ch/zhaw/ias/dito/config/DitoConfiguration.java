package ch.zhaw.ias.dito.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement( namespace = "http://ias.zhaw.ch/" )
public class DitoConfiguration { 
	@XmlElement	
  private final Input input;
	@XmlElement  
	private final Output output; 
  @XmlElement  
  private final Method method;
  @XmlElementWrapper(name = "questions") 
  @XmlElement(name = "question")
  private final List<Question> questions;
  
  private DitoConfiguration() {
	  input = null;
	  output = null;
	  method = null;
	  questions = new ArrayList<Question>();
  }  
  
  public DitoConfiguration(Input input, Output output, Method method, List<Question> questions) {
	  this.input = input;
	  this.output = output;
	  this.method = method;
	  this.questions = questions;
  }
  
  public Matrix rescale(Matrix m) {
    DVector[] rescaled = new DVector[m.getColCount()];
    for (int i = 0; i < m.getColCount(); i++) {
      DVector v = m.col(i);
      Question q = getQuestion(i);
      rescaled[i] = v.rescale(q);
    }
    return new Matrix(rescaled);
  }
  
  public Question getQuestion(int index) {
    return questions.get(index);
  }
    
  public List<Question> getQuestions() {
    //TODO here a defensive copy would be necessary to keep DitoConfiguration immutable
    //but is this method necessary??
    return questions;
  }

  public Input getInput() { 
    return input; 
  } 
  
  public Output getOutput() {
    return output;
  }
  
  public Method getMethod() {
    return method;
  }
}