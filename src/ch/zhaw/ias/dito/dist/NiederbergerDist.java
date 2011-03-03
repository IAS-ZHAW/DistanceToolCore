package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.NiederbergerOp2;

public class NiederbergerDist extends AbstractDist {
  private static final int FACTOR = 20;
  
	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new NiederbergerOp2());
		//return Math.log1p(dist.sum());
		
		return Math.log(FACTOR*dist.sum() + 1)/FACTOR;///(dist.sum() + 1);
	}
}
