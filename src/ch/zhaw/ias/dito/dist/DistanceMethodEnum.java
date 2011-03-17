package ch.zhaw.ias.dito.dist;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.ias.dito.Coding;

public class DistanceMethodEnum {

	private static List<DistanceMethodEnum> METHODS = new ArrayList<DistanceMethodEnum>();
	static {
    METHODS.add(new DistanceMethodEnum("Bhattacharyya", new BhattacharyyaDist()));
    METHODS.add(new DistanceMethodEnum("Bray-Curtis", new BrayCurtisDist()));
	  METHODS.add(new DistanceMethodEnum("Canberra", new CanberraDist()));
	  METHODS.add(new DistanceMethodEnum("Divergence", new DivergenceDist()));
	  METHODS.add(new DistanceMethodEnum("Euklid", new EuklidianDist()));
		METHODS.add(new DistanceMethodEnum("Manhattan", new ManhattanDist()));
		METHODS.add(new DistanceMethodEnum("Soergel", new SoergelDist()));
		METHODS.add(new DistanceMethodEnum("WaveHedges", new WaveHedgesDist()));
		METHODS.add(new DistanceMethodEnum("Niederberger", new NiederbergerDist()));
		
		METHODS.add(new DistanceMethodEnum("Kulzynski", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/(b+c);
      }
    }, Coding.BINARY));
    METHODS.add(new DistanceMethodEnum("Jaccard", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return a/(a+b+c);
      }
    }, Coding.BINARY));
    METHODS.add(new DistanceMethodEnum("Simple", new AbstractBinaryDist() {
      @Override
      public double distance(double a, double b, double c, double d) {
        return (a+d)/(a+b+c+d);
      }
    }, Coding.BINARY));
	}

	public static DistanceMethodEnum get(String name) {
		for (DistanceMethodEnum method : METHODS) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new IllegalArgumentException("no such distance-method: " + name);
	}
	
	public static List<DistanceMethodEnum> get(Coding c) {
    List<DistanceMethodEnum> methods = new ArrayList<DistanceMethodEnum>();
	  for (DistanceMethodEnum method : METHODS) {
      if (method.getCoding() == c) {
        methods.add(method);
      }
    }
    return methods;
  }  
	
	private final String name;
	private final DistanceSpec spec;
	private final Coding coding;

  private DistanceMethodEnum(String name, DistanceSpec spec) {
   this(name, spec, Coding.REAL);
  }
	
	private DistanceMethodEnum(String name, DistanceSpec spec, Coding coding) {
		this.name = name;
		this.spec = spec;
		this.coding = coding;
	}
	
	public String getName() {
		return name;
	}
	
	public DistanceSpec getSpec() {
		return spec;
	}
	
	public Coding getCoding() {
	  return coding;
	}
	
	@Override
	public String toString() {
	  return name;
	}
}
