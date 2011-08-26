package ch.zhaw.ias.dito.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
import javax.xml.bind.annotation.XmlType;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;

import net.jcip.annotations.NotThreadSafe;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.Matrix;
import ch.zhaw.ias.dito.util.Logger;
import ch.zhaw.ias.dito.util.Logger.LogLevel;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement( namespace = "http://ias.zhaw.ch/" )
@NotThreadSafe
@XmlType(propOrder={"input", "output" , "method", "questionConfig", "questions"})
public final class DitoConfiguration implements PropertyListener {
	private String location;
  @XmlElement	
  private Input input;
	@XmlElement  
	private Output output; 
  @XmlElement  
  private Method method;
  @XmlElement  
  private QuestionConfig questionConfig;  
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
  
  /**
   * Constructor necessary for Jaxb. Don't remove
   */
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
	  data = Matrix.createDoubleMatrix(new double[0][0]);
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
    return null;
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
  
  public Question createDefaultQuestion(int column, String name, DVector data) {
    //TODO it should be considered to use a calculated default value for the scaling
    Question q = new Question(column, name, data.getDefaultQuestionType(), 1.0, 1.0, 1.0, new double[0], 0.0);
    q.setData(data);
    return q;
  }
  
  public Question createDefaultQuestion(int column, DVector data) {
    return createDefaultQuestion(column, "Question " + column, data);
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
    clearVectorData();
    ImportWrapper wrap = Matrix.readFromFile(getInput());
    Matrix m = wrap.getMatrix();
    m = m.transpose();
    setData(wrap.getColumnNames(), m);
  }
  
  private void clearVectorData() {
    for (Question q : questions) {
      q.clearData();
    }
  }
  
  /**
   * Removes all questions without a data vector for consistancy reasons
   */
  private void removeEmptyQuestions() {
    Iterator<Question> i = questions.iterator();
    while (i.hasNext()) {
      Question q = i.next();
      if (q.getData() == null) {
        i.remove();  
      }
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
    return location != null && location.length() > 0;
  }
  
  public Matrix getData() {
    return data;
  }
  
  /**
   * not completely sure what to do here. try to match questions or clear them all and create new?
   * @param data
   */
  public void setData(List<String> columnNames, Matrix data) {
    this.data = data;
    for (int i = 0; i < data.getColCount(); i++) {
      Question q = getQuestion(i+1);
      DVector v = data.col(i);
      
      //question doesn't exist yet -> create default
      if (q == null) {
        if (columnNames.size() > i) {
          q = createDefaultQuestion(i+1, columnNames.get(i), v);
        } else {
          q = createDefaultQuestion(i+1, v);  
        }
        
        questions.add(q);
      } else {
        q.setData(v);  
      }
    }
    //all questions without a data vector must be removed
    removeEmptyQuestions();    
  }
  
  @Override
  public boolean listensTo(ConfigProperty prop) {
    return prop == ConfigProperty.INPUT_FILENAME;
  }
  
  @Override
  public void propertyChanged(ConfigProperty prop) {
    try {
      loadMatrix();
    } catch (IOException e) {
      Logger.INSTANCE.error("couldn't load specified matrix", e);
    }
  }
  
  /**
   * Method to reduce problems with memory leaks
   * after the method has been called this instance of DitoConfiguration can't be used anymore
   * All further method calls will lead to undefined behaviour.  
   */
  public void kill() {
    input = null;
    method = null;
    output = null;
    questionConfig = null;
    questions.clear();
    data = null;
  }
  
  public Object getPropertyValue(ConfigProperty p) {
    switch (p) {
    case INPUT_FILENAME: return input.getFile().getName(); 
    case METHOD_NAME: return method.getName();
    case OUTPUT_FILENAME: return output.getFilename();
    case OUTPUT_PRECISION: return output.getPrecision();
    case QUESTION_NUMBER: return data.getColCount();
    case INPUT_SIZE: return data.getRowCount();
    default: return null;
    }
  }
}