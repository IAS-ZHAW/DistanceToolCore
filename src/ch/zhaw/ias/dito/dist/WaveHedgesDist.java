package ch.zhaw.ias.dito.dist;

import ch.zhaw.ias.dito.DVector;
import ch.zhaw.ias.dito.ops.WaveHedgesOp2;

public class WaveHedgesDist extends AbstractDist {
	@Override
	public double distance(DVector a, DVector b) {
		DVector dist = a.zipWith(b, new WaveHedgesOp2());
		return dist.sum();
	}
}
