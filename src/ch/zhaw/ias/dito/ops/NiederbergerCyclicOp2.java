package ch.zhaw.ias.dito.ops;

public class NiederbergerCyclicOp2 implements Operation2 {
  @Override
  public double execute(double a, double b) {
    return Math.sin(Math.PI*Math.abs(a-b));
  }
}
