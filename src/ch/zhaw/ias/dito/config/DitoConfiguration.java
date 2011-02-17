package ch.zhaw.ias.dito.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement( namespace = "http://ias.zhaw.ch/" )
public class DitoConfiguration { 
	@XmlElement	
  private final Input input;
	@XmlElement  
	private final Output output; 
  @XmlElement  
  private final Method method;
  @XmlElement  
  private final QuestionConfig questionConfig;  
  @XmlElementWrapper(name = "questions") 
  @XmlElement(name = "question")
  private final List<Question> questions;
  
  private DitoConfiguration() {
	  input = null;
	  output = null;
	  method = null;
	  questionConfig = null;
	  questions = new ArrayList<Question>();
  }  
  
  public DitoConfiguration(Input input, Output output, Method method, QuestionConfig questionConfig, List<Question> questions) {
	  this.input = input;
	  this.output = output;
	  this.method = method;
	  this.questionConfig = questionConfig;
	  this.questions = questions;
  }
  
  public QuestionConfig getQuestionConfig() {
    return questionConfig;
  }

  public Question getQuestion(int column) {
    for (int i = 0; i < questions.size(); i++) {
      Question q = questions.get(i);
      if (q.getColumn() == column) {
        return q;
      }
    }
    Question q = createDefaultQuestion(column);
    questions.add(q);
    return q;
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
  
  public Question createDefaultQuestion(int column) {
    return new Question(column, "Question " + column, 1.0, 1.0, 1.0);
  }
}