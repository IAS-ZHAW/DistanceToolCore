package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.NiederbergerCyclicOp2;

public class NiederbergerCyclicDist extends AbstractDist {
  private static final int FACTOR = 20;
  
	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new NiederbergerCyclicOp2());
		
		return FACTOR*dist.sum();
	}
}
