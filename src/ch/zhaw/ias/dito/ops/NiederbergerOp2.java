package ch.zhaw.ias.dito.ops;

public class NiederbergerOp2 implements Operation2 {
  @Override
  public double execute(double a, double b) {
    return Math.abs(a-b);//Math.log(2000*Math.abs(a-b) + 1);
  }
}
