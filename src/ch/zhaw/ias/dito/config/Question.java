package ch.zhaw.ias.dito.config;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import net.jcip.annotations.NotThreadSafe;

import ch.zhaw.ias.dito.Coding;
import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.QuestionType;

/**
 * TODO it should be considered to cache the values of min, max and values for performance reasons
 * @author Thomas
 *
 */
@NotThreadSafe
public final class Question {
	private int column;
	private String name;
	private Double scaling;
	private Double questionWeight;
	private Double distanceWeight;
  private QuestionType questionType;
  private Boolean enabled;

  private DVector data;
	
  public Question() {
    this(-1, "", QuestionType.ORDINAL, 1.0, 1.0, 1.0);
  }
	
	public Question(Integer column, String name, QuestionType questionType, Double scaling, Double questionWeight,
      Double distanceWeight) {
    this.column = column;
    this.name = name;
    this.scaling = scaling;
    this.questionWeight = questionWeight;
    this.distanceWeight = distanceWeight;
    this.questionType = questionType;
    this.enabled = true;
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
      && distanceWeight.equals(q.distanceWeight)
      && enabled.equals(q.enabled);
  }

  public Object getDistinctValues() {
    Set<Double> values = new TreeSet<Double>();
    data.addValuesToCollection(values);
    
    StringBuilder sb = new StringBuilder();
    Iterator<Double> i = values.iterator();
    for (; i.hasNext(); ) {
      sb.append(Double.toString(i.next()));
      if (i.hasNext() == true) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  public Object getValue(TableColumn col) {
    if (col == TableColumn.NUMBER) {
      return getColumn();
    } else if (col == TableColumn.NAME) {
      return getName();
    } else if (col == TableColumn.VALUES) {
      return getDistinctValues();
    } else if (col == TableColumn.MIN) {
      return min();
    } else if (col == TableColumn.MAX) {
      return max();
    } else if (col == TableColumn.TYPE) {
      return questionType;
    } else if (col == TableColumn.DISTANCE_WEIGHT) {
      return getDistanceWeight();
    } else if (col == TableColumn.QUESTION_WEIGHT) {
      return getQuestionWeight();
    } else if (col == TableColumn.SCALING) {
      return getScaling();
    }
    throw new IllegalArgumentException("this is the end of the world");
  }

  public void setValue(TableColumn col, Object value) {
    if (col == TableColumn.NAME) {
      name = value.toString();
    } else if (col == TableColumn.DISTANCE_WEIGHT) {
      distanceWeight = Double.parseDouble(value.toString());
    } else if (col == TableColumn.QUESTION_WEIGHT) {
      questionWeight = Double.parseDouble(value.toString());
    } else if (col == TableColumn.SCALING) {
      scaling = Double.parseDouble(value.toString());
    } else if (col == TableColumn.TYPE) {
      questionType = (QuestionType) value;
    } else {
      throw new IllegalArgumentException("column " + col + " is not editable");
    }
  }
  
  public double min() {
    return data.min();
  }
  
  public double max() {
    return data.max();
  }

  public void setData(DVector data) {
    this.data = data;
    QuestionType type = data.getDefaultQuestionType();
    // set new type if QuestionType is Binary or Metric
    if (type == QuestionType.BINARY || type == QuestionType.METRIC) {
      setQuestionType(type);
    } else if (questionType == QuestionType.BINARY || questionType == QuestionType.METRIC) {
      setQuestionType(type);
    }
  }
  
  public void clearData() {
    this.data = null;
  }
  
  @XmlTransient
  public DVector getData() {
    return data;
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
  
  public void setScaling(Double scaling) {
    this.scaling = scaling;
  }
  
  public Double getQuestionWeight() {
    return questionWeight;
  }

  public void setQuestionWeight(Double questionWeight) {
    this.questionWeight = questionWeight;
  }
  
  public Double getDistanceWeight() {
    return distanceWeight;
  }
  
  public void setQuestionType(QuestionType questionType) {
    this.questionType = questionType;
  }
  
  public QuestionType getQuestionType() {
    return questionType;
  }
  
  public void setDistanceWeight(Double distanceWeight) {
    this.distanceWeight = distanceWeight;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setColumn(int column) {
    this.column = column;
  }
  
  public Boolean getEnabled() {
    return enabled;
  }
  
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }
  
  private DVector[] splitUpCoding() {
    int min = (int) data.min();
    int max = (int) data.max();
    int length = max - min + 1;
    double[][] values = new double[length][data.length()];
    for (int i = 0; i < data.length(); i++) {
      double value = data.component(i);
      for (int j = 0; j < length; j++) {
        if (Double.isNaN(value)) {
          values[j][i] = Double.NaN;
        } else if (((int) value) == (j + min)) {
          values[j][i] = 1;
        } else {
          values[j][i] = 0;
        }
      }
    }
    DVector[] vecs = new DVector[length];
    for (int j = 0; j < length; j++) {
      vecs[j] = new DVector(values[j]);
    }
    return vecs;
  }

  public DVector[] recode(Coding coding) {
    if (coding == Coding.BINARY) {
      if (questionType == QuestionType.BINARY) {
        return new DVector[] {data.toBinary()};
      } else if (questionType == QuestionType.NOMINAL || questionType == QuestionType.ORDINAL) {
        return splitUpCoding();
      }
      throw new IllegalStateException("not implemented yet");
    } else if (coding == Coding.REAL){
      if (questionType == QuestionType.METRIC || questionType == QuestionType.ORDINAL) {
        return new DVector[] {data}; //no recoding necessary
      } else if (questionType == QuestionType.BINARY) {
        return new DVector[] {data.toBinary()};
      } else if (questionType == QuestionType.NOMINAL) {
        return splitUpCoding();
      }
      throw new IllegalStateException("not implemented yet");
    }
    throw new IllegalArgumentException("unsupported coding " + coding);
  }
}
