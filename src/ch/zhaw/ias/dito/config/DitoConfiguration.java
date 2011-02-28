package ch.zhaw.ias.dito.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ch.zhaw.ias.dito.Matrix;

/**
 * This class is NOT threadsafe.
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement( namespace = "http://ias.zhaw.ch/" )
public class DitoConfiguration {
	private String location;
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
  private Matrix data;
  
  public static DitoConfiguration loadFromFile(String filename) throws JAXBException, FileNotFoundException {
    return loadFromFile(new File(filename));
  }
  
  public static DitoConfiguration loadFromFile(File file) throws JAXBException, FileNotFoundException {
    JAXBContext context = JAXBContext.newInstance(DitoConfiguration.class);
    Unmarshaller um = context.createUnmarshaller(); 
    return (DitoConfiguration) um.unmarshal(new FileReader(file)); 
  }  
  
  public static void saveToFile(String filename, DitoConfiguration config) throws JAXBException {
    JAXBContext context = JAXBContext.newInstance(DitoConfiguration.class);
    Marshaller m = context.createMarshaller();
    m.setProperty("jaxb.formatted.output", true);
    m.marshal(config, new File(filename));
  }
  
  public static DitoConfiguration createEmpty() {
    return new DitoConfiguration(new Input(), new Output(), new Method(), new QuestionConfig(), new ArrayList<Question>());
  }
  
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
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DitoConfiguration == false) {
      return false;
    }
    DitoConfiguration cd = (DitoConfiguration) obj;
    return input.equals(cd.input) 
      && output.equals(cd.output)
      && method.equals(cd.method)
      && questionConfig.equals(cd.questionConfig)
      && questions.equals(cd.questions);
  }
  
  public void loadMatrix() throws IOException {
    data = Matrix.readFromFile(new File(getInput().getFilename()), getInput().getSeparator());
    data = data.transpose();
    for (int i = 0; i < data.getColCount(); i++) {
      getQuestion(i+1).setDVector(data.col(i));
    }
  }
  
  public String getLocation() {
    return location;
  }
  
  public void setLocation(String location) {
    this.location = location;
  }
  
  public void save() throws JAXBException {
    saveToFile(location, this);
  }

  public boolean hasLocation() {
    return false;
  }
  
  public Object getProperty(String key) {
    return "Dummy";
  }
}