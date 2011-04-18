package ch.zhaw.ias.dito.ops;

public class NiederbergerOp2 implements Operation2 {
  @Override
  public double execute(double a, double b) {
    return Math.log(Math.abs(a-b) + 1)/(1 + Math.min(a, b)*Math.min(a, b));
  }
}
