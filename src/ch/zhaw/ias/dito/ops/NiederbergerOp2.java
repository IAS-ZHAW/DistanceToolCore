package ch.zhaw.ias.dito.ops;

public class NiederbergerOp2 implements Operation2 {
  @Override
  public double execute(double a, double b) {
    return Math.log1p(Math.abs(a-b));
  }
}
