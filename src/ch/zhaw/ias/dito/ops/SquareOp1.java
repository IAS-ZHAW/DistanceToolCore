package ch.zhaw.ias.dito.ops;

public class SquareOp1 implements Operation1 {
  @Override
  public double execute(double a) {
    //don't use Math.pow() this is much faster
    return a * a;
  }
}
