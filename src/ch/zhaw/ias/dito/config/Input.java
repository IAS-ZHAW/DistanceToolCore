package ch.zhaw.ias.dito.config;

import java.io.File;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
@XmlType(propOrder={"filename", "separator", "questionTitles", "allQuestions", "startQuestion", "endQuestion", "allSurveys", "startSurvey", "endSurvey"})
public final class Input { 
	private File file;
  private Character separator;
  private boolean questionTitles;
  private boolean allQuestions;
  private int startQuestion;
  private int endQuestion;
  private boolean allSurveys;
  private int startSurvey;
  private int endSurvey;  

  public Input() {
		file = new File("");
		separator = ';';
		questionTitles = false;
		allQuestions = true;
		startQuestion = -1;
		endQuestion = -2;
		allSurveys = true;
		startSurvey = -1;
		endSurvey = -2;
	}
	
  public Input(String file, char separator, boolean questionTitles, boolean allQuestions, int startQuestion, int endQuestion, boolean allSurveys, int startSurvey, int endSurvey) {
    this(new File(file), separator, questionTitles, allQuestions, startQuestion, endQuestion, allSurveys, startSurvey, endSurvey);
  }
  
	 public Input(File file, char separator, boolean questionTitles, boolean allQuestions, int startQuestion, int endQuestion, boolean allSurveys, int startSurvey, int endSurvey) {
	     this.file = file;
	     this.separator = separator;
	     this.questionTitles = questionTitles;
	     this.allQuestions = allQuestions;
	     this.startQuestion = startQuestion;
	     this.endQuestion = endQuestion;
	     this.allSurveys = allSurveys;
	     this.startSurvey = startSurvey;
	     this.endSurvey = endSurvey;
	 }
	
	public String getFilename() {
		return file.getAbsolutePath();
	}
	
	public Character getSeparator() {
	  //very good idea to just access index 0
	  //shows that the engineer should be fired
	  //TODO think of something better
	  return separator;//.charAt(0);
	}
	
	@Override
	public boolean equals(Object obj) {
	  if (obj instanceof Input == false) {
	    return false;
	  }
	  Input i = (Input) obj;
	  return file.equals(i.file) 
	    && separator.equals(i.separator)
	    && questionTitles == i.questionTitles
	    && allQuestions == i.allQuestions
	    && allSurveys == i.allSurveys
	    && startQuestion == i.startQuestion
	    && endQuestion == i.endQuestion
	    && startSurvey == i.startSurvey
	    && endSurvey == i.endSurvey; 
	}

	@XmlTransient
	public File getFile() {
	  return file;
	}
	
  public void setFilename(String filename) {
    this.file = new File(filename);
  }

  public void setSeparator(Character separator) {
    this.separator = separator;//Character.toString(separator);
  }

  public boolean isQuestionTitles() {
    return questionTitles;
  }

  public void setQuestionTitles(boolean questionTitles) {
    this.questionTitles = questionTitles;
  }

  public boolean isAllQuestions() {
    return allQuestions;
  }

  public void setAllQuestions(boolean allQuestions) {
    this.allQuestions = allQuestions;
  }

  public int getStartQuestion() {
    return startQuestion;
  }

  public void setStartQuestion(int startQuestion) {
    this.startQuestion = startQuestion;
  }

  public int getEndQuestion() {
    return endQuestion;
  }

  public void setEndQuestion(int endQuestion) {
    this.endQuestion = endQuestion;
  }

  public boolean isAllSurveys() {
    return allSurveys;
  }

  public void setAllSurveys(boolean allSurveys) {
    this.allSurveys = allSurveys;
  }

  public int getStartSurvey() {
    return startSurvey;
  }

  public void setStartSurvey(int startSurvey) {
    this.startSurvey = startSurvey;
  }

  public int getEndSurvey() {
    return endSurvey;
  }

  public void setEndSurvey(int endSurvey) {
    this.endSurvey = endSurvey;
  }	
  
  
}