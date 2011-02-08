package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.CanberraOp;

public class CanberraDist extends AbstractDist {

	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new CanberraOp());
		return dist.sum();
	}
}
