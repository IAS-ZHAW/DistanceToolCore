package ch.zhaw.ias.dito;

import java.util.List;

public class VectorFactory {
  
  public static boolean isBinaryData(List values) {
    for (Object val : values) {
      if (val.getClass() != Boolean.class) {
        return false;
      }
    }
    return true;
  }
  
  /*public static DitoVector createVector(List values) {
    if (isBinaryData(values) == true) {
      return new BVector((Boolean[]) values.toArray(new Boolean[0]));
    } else {
      return new DVector((Double[]) values.toArray(new Double[0]));
    }
  }*/
  
  private VectorFactory() {
    
  }
}
