package ch.zhaw.ias.dito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.jcip.annotations.Immutable;

import ch.zhaw.ias.dito.ops.AddOp2;
import ch.zhaw.ias.dito.ops.FilterOp1;
import ch.zhaw.ias.dito.ops.MaxOp2;
import ch.zhaw.ias.dito.ops.MinOp2;
import ch.zhaw.ias.dito.ops.Operation1;
import ch.zhaw.ias.dito.ops.Operation2;
import ch.zhaw.ias.dito.ops.ScalarProduct2;
import ch.zhaw.ias.dito.ops.ScaleOp1;
import ch.zhaw.ias.dito.ops.VarianceOp1;

/**
 * In respect of possible concurrent calculation this class is immutable.
 * It's absolutely necessary that this class is immutable
 * @author Thomas Niederberger (nith) - institute of applied simulation (IAS)
 * TODO this class could implement some low level concurrency mechanism to improve performance.
 */
public final class DVector {
	private final double[] values;
	
	/**
	 * Creates a random noise vector of length n. The noise has uniform distribution ranging from 0 to maxNoise
	 * @param len
	 * @param maxNoise
	 * @return
	 */
	public static DVector generateRandomVector(int len, double maxNoise) {
    Random r = new Random();
    double[] values = new double[len];
	  for (int i = 0; i < len; i++) {
      values[i] = r.nextDouble() * maxNoise;
    }
	  return new DVector(values);
	}
	
	public DVector(double... values) {
	  //values must be copied to new array to ensure immutability.
		this.values = Arrays.copyOf(values, values.length);
	}
	
	 public DVector(List<Double> values) {
	    this.values = new double[values.size()];
	    for (int i = 0; i < values.size(); i++) {
	      this.values[i] = values.get(i);
	    }
	  }
	
	public double sum() {
		return foldl1(new AddOp2());
	}
	
  public double mean() {
    //maybe better use filtered length?
    return foldl1(new AddOp2())/values.length;
  }
  
  /**
   * calculates the variance for this vector, but doesn't scale the results in the end (does not divide by (n-1)).
   * @return
   */
  public double unscaledVariance() {
    double mean = mean();
    return map(new VarianceOp1(mean)).sum();
  }

  public double scalarProduct(DVector v) {
    DVector multiplied = zipWith(v, new ScalarProduct2());
    return multiplied.sum();
  }
  
  public DVector removeMean() {
    double mean = mean();
    return map(new ScaleOp1(1.0, mean));
  }
	
	public double max() {
		return foldl1(new MaxOp2());
	}
	
	public double min() {
		return foldl1(new MinOp2());
	}	
	
	public DVector add(DVector v) {
		return zipWith(v, new AddOp2());
	}
	
	public int length() {
		return values.length;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DVector == false) {
			return false;
		}
		DVector v = (DVector) obj;
		//probably not necessary since values.equals will check anyway
		if (v.length() != this.length()) {
			return false;
		}
		return Arrays.equals(values, v.values);
	}

	public double component(int index) {
		return values[index];
	}
	
	/**
	 * Implements a Haskell like zipWith operation
	 * @param v
	 * @param o
	 * @return
	 */
	public DVector zipWith(DVector v, Operation2 o) {
		if (v.values.length != values.length) {
			throw new IllegalArgumentException();
		}
		double[] c = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			c[i] = o.execute(values[i], v.values[i]);
		}
		return new DVector(c);
	}
	
  /**
   * Implements a Haskell like foldl operation
   * @param v
   * @param o
   * @return
   */	
	public double foldl(Operation2 op, double initial) {
		double result = initial;

		for (int i = 0; i < values.length; i++) {
			if (Double.isNaN(values[i])) {
				continue; //ignore NaN
			} else {
				result = op.execute(result, values[i]);
			}
		}
		return result;
	}
	
  /**
   * Implements a Haskell like foldl1 operation
   * @param v
   * @param o
   * @return
   */	
	public double foldl1(Operation2 op) {
		double result = Double.NaN;

		for (int i = 0; i < values.length; i++) {
			if (Double.isNaN(values[i])) {
				continue; //ignore NaN
			} else {
				if (Double.isNaN(result)) {
					result = values[i];
				} else {
					result = op.execute(result, values[i]);
				}
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
	  StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			if ((i + 1) < values.length) {
				sb.append(',');
			}
		}
		return sb.append(')').toString();
	}

	/**
	 * Returns the length of this vector subtracted by the number of NaN values
	 * @return
	 */
	public int filteredLength() {
		return (int) foldl(new Operation2() {
			@Override
			public double execute(double a, double b) {
				return a + 1;
			}
		}, 0);
	}

  /**
   * Implements a Haskell like map operation
   * @param v
   * @param o
   * @return
   */ 	
	public DVector map(Operation1 op) {
	  double[] c = new double[values.length];
    for (int i = 0; i < values.length; i++) {
      if (Double.isNaN(values[i])) {
        c[i] = Double.NaN;
      } else {
        c[i] = op.execute(component(i));
      }
    }
    return new DVector(c);
	}
		
	public DVector rescale(double multiplier, double offset) {
	  return map(new ScaleOp1(multiplier, offset));
	}
	
	/**
	 * returns a rescaled copy of this vector. All values will be ranging form 0 to 1.
	 * the scaling is done using the following forumla: (value-min)/(max-min)
	 * @return
	 */
	public DVector autoRescale() {
	  double max = max();
	  double min = min();
	  return rescale(1 / (max - min), min);
	}

	/**
	 * Converts this vector into a binary vector
	 * NaN and negative values will result in NaN
	 * 0 will result in 0
	 * all remaining values will result in 1
	 * @return
	 */
  public DVector toBinary() {
    double[] c = new double[values.length];
    for (int i = 0; i < values.length; i++) {
      if (Double.isNaN(values[i]) || values[i] < 0) {
        c[i] = Double.NaN;
      } else if (values[i] == 0) {
        c[i] = 0;
      } else {
        c[i] = 1;
      }
    }
    return new DVector(c);
  }
  
  public void addValuesToCollection(Collection<Double> c) {
    for (int i = 0; i < values.length; i++) {
      c.add(values[i]);  
    }
  }

  public double[] getValues() {
    return Arrays.copyOf(values, values.length);
  }
  
  /**
   * find an appropriate default question type for this question
   * if a vector contains only 0 and 1 values it is supposed to be binary
   * if a vector contains values with decimal places it is supposed to be METRIC 
   * all other values are supposed to be ORDINAL
   * @return
   */
  public QuestionType getDefaultQuestionType() {
    // default is binary
    QuestionType type = QuestionType.BINARY;
    for (int i = 0; i < values.length; i++) {
      if (Double.isNaN(values[i])) {
        continue;
      }
      if (values[i] - ((int) values[i]) != 0.0) {
        // if a value with decimal places occurs the question is supposed to be metric
        return QuestionType.METRIC;
      } else if (values[i] != 0 && values[i] != 1) {
        // if a value different from 0 or 1 occurs the vector can't be binary anymore -> ORDINAL
        type = QuestionType.ORDINAL;
      }
    }
    return type;
  }
  
  /**
   * 
   * @return
   */
  private DVector[] splitUpCoding() {
    int min = (int) min();
    int max = (int) max();
    int length = max - min + 1;
    double[][] values = new double[length][length()];
    for (int i = 0; i < length(); i++) {
      double value = component(i);
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
  
  private DVector[] splitUpCoding(int numOfGroups) {
    double min = min();
    double max = max();
    double step = (max - min)/numOfGroups;
    double[][] values = new double[numOfGroups][length()];
    for (int i = 0; i < length(); i++) {
      double value = component(i);

      for (int j = 0; j < numOfGroups; j++) {
        double upperBoundary = min + ((j + 1) * step);
        if (Double.isNaN(value)){
          values[j][i] = Double.NaN;
        }
        // ensure max is not lost due to rounding problems
        if (j + 1 == numOfGroups) {
          upperBoundary = max;
        }
        if (value <= upperBoundary) {
          values[j][i] = 1;
          break;
        } 
      }
    }
    
    DVector[] vecs = new DVector[numOfGroups];
    for (int j = 0; j < numOfGroups; j++) {
      vecs[j] = new DVector(values[j]);
    }
    return vecs;
  }

  /**
   * Will recode this Vector to BINARY or REAL values depending on the question type. In some cases components will be split up to multiple  
   * (see separate documentation)
   * @param coding
   * @param questionType
   * @return
   */
  public DVector[] recode(Coding coding, QuestionType questionType) {
    if (coding == Coding.BINARY) {
      if (questionType == QuestionType.BINARY) {
        return new DVector[] {toBinary()};
      } else if (questionType == QuestionType.NOMINAL || questionType == QuestionType.ORDINAL) {
        return splitUpCoding();
      } else if (questionType == QuestionType.METRIC) {
        return splitUpCoding(10);
      }
      throw new IllegalStateException("not implemented yet");
    } else if (coding == Coding.REAL){
      if (questionType == QuestionType.METRIC || questionType == QuestionType.ORDINAL) {
        return new DVector[] {this}; //no recoding necessary
      } else if (questionType == QuestionType.BINARY) {
        return new DVector[] {this.toBinary()};
      } else if (questionType == QuestionType.NOMINAL) {
        return splitUpCoding();
      } 
      throw new IllegalStateException("not implemented yet");
    }
    throw new IllegalArgumentException("unsupported coding " + coding);
  }

  public DVector exclude(double... values) {
    return map(new FilterOp1(values));
  }

  /**
   * Checks whether the vector contains only one constant value or whether it contains different values.
   * @return
   */
  public boolean isConstant() {
    if (values.length < 2) {
      return true;
    }
    double prev = values[0];
    for (int i = 1; i < values.length; i++) {
      if (prev != values[i]) {
        return false;
      }
      prev = values[i];
    }
    return true;
  }
  
  /**
   * Checks whether this vector contains special values.
   * Special values are NaN, Negative Infinity, Positive Infinity
   * @return
   */
  public boolean containsSpecial() {
    for (int i = 0; i < values.length; i++) {
      if (Double.isInfinite(values[i])) {
        return true;
      } else if (Double.isNaN(values[i])) {
        return true;
      }
    }
    return false;
  }
}