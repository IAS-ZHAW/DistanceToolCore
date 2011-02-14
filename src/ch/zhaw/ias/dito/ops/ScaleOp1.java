package ch.zhaw.ias.dito.ops;

public class ScaleOp1 implements Operation1 {
  private double multiplier;
  
  public ScaleOp1(double multiplier) {
    this.multiplier = multiplier;
  }
  
  @Override
  public double execute(double a) {
    return multiplier * a;
  }
}
