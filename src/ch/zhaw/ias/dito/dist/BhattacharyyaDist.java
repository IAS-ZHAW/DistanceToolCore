package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.BhattacharyyaOp2;

public class BhattacharyyaDist extends AbstractDist {
	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new BhattacharyyaOp2());
		return Math.sqrt(dist.sum());
	}
}
