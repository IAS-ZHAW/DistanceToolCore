package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;

public abstract class AbstractBinaryDist implements DistanceSpec {
  @Override
  public double distance(DVector aVec, DVector bVec) {
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;
    for (int i = 0; i < aVec.length(); i++) {
      double vA = aVec.component(i);
      double vB = bVec.component(i);
      if (Double.isNaN(vA) || Double.isNaN(vB)) {
        continue;
      } else if (vA == 1.0 && vB == 1.0) {
        a++;
      } else if (vA == 1.0 && vB == 0.0) {
        b++;
      } else if (vA == 0.0 && vB == 1.0) {
        c++;
      } else if (vA == 0.0 && vB == 0.0) {
        d++;
      } else {
        throw new IllegalArgumentException("value " + vA + " or " + vB + " is not allowed");
      }
    }
    return distance(a, b, c, d);
  }
  
  public abstract double distance(double a, double b, double c, double d);
  
  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error();
    }
  }
}
