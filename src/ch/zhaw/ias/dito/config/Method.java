package ch.zhaw.ias.dito.config;

import net.jcip.annotations.NotThreadSafe;
import ch.zhaw.ias.dito.dist.DistanceMethodEnum;

@NotThreadSafe
public final class Method {
  private DistanceMethodEnum method; 
  private boolean parallel;
  private int numberOfThreads;
  private boolean useRandomSample;
  private int sampleSize;
  
  public Method() {
    this(DistanceMethodEnum.get("Euklid"));
  }
  
  public Method(DistanceMethodEnum method) {
    this(method, true, 5);
  }
  
  public Method(DistanceMethodEnum method, boolean parallel, int numberOfThreads) {
    this(method, parallel, numberOfThreads, false, 1000);
  }
  
  public Method(DistanceMethodEnum method, boolean parallel, int numberOfThreads, boolean useRandomSample, int sampleSize) {
    this.method = method;
    this.parallel = parallel;
    this.numberOfThreads = numberOfThreads;
    this.useRandomSample = useRandomSample;
    this.sampleSize = sampleSize;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Method == false) {
      return false;
    }
    Method m = (Method) obj;
    return method == m.method 
      && parallel == m.parallel
      && numberOfThreads == m.numberOfThreads
      && useRandomSample == m.useRandomSample
      && sampleSize == m.sampleSize;
  }
  
  public String getName() {
    return method.getName();
  }
  
  public void setName(String name) {
    method = DistanceMethodEnum.get(name);
  }
  
  public void setNumberOfThreads(int numberOfThreads) {
    this.numberOfThreads = numberOfThreads;
  }
  
  public int getNumberOfThreads() {
    return numberOfThreads;
  }
  
  public boolean isParallel() {
    return parallel;
  }
  
  public void setParallel(boolean parallel) {
    this.parallel = parallel;
  }
  
  public int getSampleSize() {
    return sampleSize;
  }
  
  public void setSampleSize(int sampleSize) {
    this.sampleSize = sampleSize;
  }
  
  public void setUseRandomSample(boolean useRandomSample) {
    this.useRandomSample = useRandomSample;
  }
  
  public boolean isUseRandomSample() {
    return useRandomSample;
  }
}
