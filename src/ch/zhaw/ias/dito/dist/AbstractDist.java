package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;

public abstract class AbstractDist implements DistanceSpec, Cloneable {
		
	@Override
	public Object clone() {
	  try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error();
    }
	}
}
