package ch.zhaw.ias.dito;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ch.zhaw.ias.dito.ops.AddOp2;
import ch.zhaw.ias.dito.ops.MaxOp2;
import ch.zhaw.ias.dito.ops.MinOp2;
import ch.zhaw.ias.dito.ops.Operation1;
import ch.zhaw.ias.dito.ops.Operation2;
import ch.zhaw.ias.dito.ops.ScaleOp1;

public final class DVector {
	private final double[] values;
	
	public DVector(double... values) {
		this.values = values;
	}
	
  public DVector(Double... values) {
    this.values = new double[values.length];
    for (int i = 0; i < values.length; i++) {
      this.values[i] = values[i];
    }
  }	
	
	public double sum() {
		return foldl1(new AddOp2());
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
			if (i+1 < values.length) {
				sb.append(',');
			}
		}
		return sb.append(')').toString();
	}

	public int filteredLength() {
		return (int) foldl(new Operation2() {
			
			@Override
			public double execute(double a, double b) {
				return a + 1;
			}
		}, 0);
	}

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
	
	/*@Override
	public int hashCode() {
	  int result = 17;
	  for (int i = 0; i < values.length; i++) {
	    //TODO find out what Kent Beck proposes here???
	    Long f = Double.doubleToLongBits(values[i]);
	    result = 37 * result + (int) (f^(f>>>32));
	  }
	  return result;
	}*/
	
	public DVector rescale(double multiplier, double offset) {
	  return map(new ScaleOp1(multiplier, offset));
	}
	
	public DVector autoRescale() {
	  double max = max();
	  double min = min();
	  return rescale(1/(max-min), min);
	}

  public DVector toBinary() {
    double[] c = new double[values.length];
    for (int i = 0; i < values.length; i++) {
      if (Double.isNaN(values[i]) || values[i] == -1) {
        c[i] = Double.NaN;
      } else if (values[i] == 0){
        c[i] = 0;
      } else {
        c[i] = 1;
      }
    }
    return new DVector(c);
  }
  
  public void addValues(Collection<Double> c) {
    for (int i = 0; i < values.length; i++) {
      c.add(values[i]);  
    }
  }
}