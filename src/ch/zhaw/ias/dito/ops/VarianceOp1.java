package ch.zhaw.ias.dito.ops;

public class VarianceOp1  implements Operation1 {
  private double mean;
  
  public VarianceOp1(double mean) {
    this.mean = mean;
  }
  
  @Override
  public double execute(double a) {
    double temp = (a - mean);
    return temp * temp;
  }
}
