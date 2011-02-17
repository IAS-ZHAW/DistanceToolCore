package ch.zhaw.ias.dito.dist;

import java.util.ArrayList;
import java.util.List;

public class DistanceMethodEnum {

	private static List<DistanceMethodEnum> METHODS = new ArrayList<DistanceMethodEnum>();
	static {
		METHODS.add(new DistanceMethodEnum("Euklid", new EuklidianDist()));
		METHODS.add(new DistanceMethodEnum("Manhattan", new ManhattanDist()));
		METHODS.add(new DistanceMethodEnum("Canberra", new CanberraDist()));
		METHODS.add(new DistanceMethodEnum("Bhattacharyya", new BhattacharyyaDist()));
		METHODS.add(new DistanceMethodEnum("WaveHedges", new WaveHedgesDist()));
		METHODS.add(new DistanceMethodEnum("Niederberger", new NiederbergerDist()));
		METHODS.add(new DistanceMethodEnum("Kulzynski", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/(b+c);
      }
    }, true));
    METHODS.add(new DistanceMethodEnum("Jaccard", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/(a+b+c);
      }
    }, true));
    METHODS.add(new DistanceMethodEnum("Simple", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return (a+d)/(a+b+c+d);
      }
    }, true));    
	}
	
	public static DistanceMethodEnum get(String name) {
		for (DistanceMethodEnum method : METHODS) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new IllegalArgumentException("no such distance-method: " + name);
	}
	
	private String name;
	private DistanceSpec spec;
	private boolean isBinary;

	 private DistanceMethodEnum(String name, DistanceSpec spec) {
	   this(name, spec, false);
	  }
	
	private DistanceMethodEnum(String name, DistanceSpec spec, boolean isBinary) {
		this.name = name;
		this.spec = spec;
		this.isBinary = isBinary;
	}
	
	public String getName() {
		return name;
	}
	
	public DistanceSpec getSpec() {
		return spec;
	}
	
	public boolean isBinary() {
	  return isBinary;
	}
}
