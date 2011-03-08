package ch.zhaw.ias.dito.ops;

public class MultiplyOp1  implements Operation1 {
  private double multiplier;
  
  public MultiplyOp1(double multiplier) {
    this.multiplier = multiplier;
  }
  
  @Override
  public double execute(double a) {
    return multiplier * a;
  }
}
