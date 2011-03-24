package ch.zhaw.ias.dito.ops;

import java.util.Arrays;

public final class FilterOp1 implements Operation1 {
  private final double[] values;
  
  public FilterOp1(double... values) {
    //array must be cloned to ensure immutability
    this.values = Arrays.copyOf(values, values.length);
    //array must be sorted to make use of the Arrays.binarySearch method
    Arrays.sort(this.values);
  }

  @Override
  public double execute(double a) {
    if (Arrays.binarySearch(values, a) >= 0) {
      return Double.NaN;
    } else {
      return a;
    }
  }
}
