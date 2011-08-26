package ch.zhaw.ias.dito.config;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.jcip.annotations.NotThreadSafe;

import ch.zhaw.ias.dito.Coding;
import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.QuestionType;
import ch.zhaw.ias.dito.Utils;

/**
 * TODO it should be considered to cache the values of min, max and values for performance reasons
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 */
@NotThreadSafe
@XmlType(propOrder={"column", "name" , "questionType", "enabled", "scaling", "questionWeight", "distanceWeight", "exclude", "offset"})
public final class Question {
	private int column;
	private String name;
	private Double scaling;
	private Double questionWeight;
	private Double distanceWeight;
  private QuestionType questionType;
  private Boolean enabled;
  @XmlElementWrapper(name = "excludeValues") 
  @XmlElement(name = "value")
  private double[] exclude;
  private Double offset;

  private DVector data;
	
  public Question() {
    this(-1, "", QuestionType.ORDINAL, 1.0, 1.0, 1.0, new double[0], 0.0);
  }
	
	public Question(Integer column, String name, QuestionType questionType, Double scaling, Double questionWeight,
      Double distanceWeight, double[] exclude, Double offset) {
    this.column = column;
    this.name = name;
    this.scaling = scaling;
    this.questionWeight = questionWeight;
    this.distanceWeight = distanceWeight;
    this.questionType = questionType;
    this.enabled = true;
    this.exclude = exclude;
    this.offset = offset;
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
      && enabled.equals(q.enabled)
      && Arrays.equals(exclude, q.exclude)
      && offset.equals(q.offset);
  }

  public Object getDistinctValues() {
    Set<Double> values = new TreeSet<Double>();
    data.addValuesToCollection(values);
    return Utils.toCommaSeparatedString(values.iterator());
  }

  public Object getValue(TableColumn col) {
    if (col == TableColumn.NUMBER) {
      return getColumn();
    } else if (col == TableColumn.NAME) {
      return getName();
    } else if (col == TableColumn.VALUES) {
      return getDistinctValues();
    } else if (col == TableColumn.EXCLUDE) {
      return Utils.toCommaSeparatedString(exclude);
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
    } else if (col == TableColumn.OFFSET) {
      return getOffset();
    } 
    throw new IllegalArgumentException("this is the end of the world");
  }

  public void setValue(TableColumn col, Object value) {
    if (col == TableColumn.NAME) {
      name = value.toString();
    } else if (col == TableColumn.EXCLUDE) {
      exclude = (double[]) value;
    } else if (col == TableColumn.DISTANCE_WEIGHT) {
      distanceWeight = Double.parseDouble(value.toString());
    } else if (col == TableColumn.QUESTION_WEIGHT) {
      questionWeight = Double.parseDouble(value.toString());
    } else if (col == TableColumn.SCALING) {
      scaling = Double.parseDouble(value.toString());
    } else if (col == TableColumn.TYPE) {
      questionType = (QuestionType) value;
    } else if (col == TableColumn.OFFSET) {
      offset = Double.parseDouble(value.toString());
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

  /**
   * Sets a new data vector. For consistency reasons this changes other attributes of Question (i.e. QuestionType) too.
   * TODO maybe other values must be changed too.
   * @param data
   */
  public void setData(DVector data) {
    this.data = data;
    QuestionType type = data.getDefaultQuestionType();
    // set new type if QuestionType is Binary or Metric
    // is it strictly necessary to set the type to binary??
    /*if (type == QuestionType.BINARY || type == QuestionType.METRIC) {
      setQuestionType(type);
    } else if (questionType == QuestionType.BINARY || questionType == QuestionType.METRIC) {
      setQuestionType(type);
    }*/
    if (type == QuestionType.BINARY || type == QuestionType.METRIC) {
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

  public DVector[] recode(Coding coding) {
    return data.recode(coding, questionType);
  }
  
  @XmlTransient
  public double[] getExclude() {
    return exclude;
  }
  
  public void setExclude(double[] exclude) {
    this.exclude = exclude;
  }
  
  public DVector getExcludedVector() {
    return data.exclude(exclude);
  }
  
  public Double getOffset() {
    return offset;
  }
  
  public void setOffset(Double offset) {
    this.offset = offset;
  }
}
