package ch.zhaw.ias.dito.ops;

public class ScaleOp1 implements Operation1 {
  private double multiplier;
  private double offset;
  
  public ScaleOp1(double multiplier, double offset) {
    this.multiplier = multiplier;
    this.offset = offset;
  }
  
  @Override
  public double execute(double a) {
    return multiplier * (a - offset);
  }
}
