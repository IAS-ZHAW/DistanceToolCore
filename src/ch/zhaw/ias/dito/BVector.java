package ch.zhaw.ias.dito;

import java.util.BitSet;

/*public final class BVector implements DitoVector {
  private final BitSet bits;
  private int length;
  
  public static BitSet toBitSet(boolean... vals) {
    BitSet bits = new BitSet(vals.length);
    for (int i = 0; i < vals.length; i++) {
      if (vals[i] == true) {
        bits.set(i, true);
      }
    }
    return bits;
  }
  
  public static BitSet toBitSet(Boolean... vals) {
    BitSet bits = new BitSet(vals.length);
    for (int i = 0; i < vals.length; i++) {
      if (vals[i] == true) {
        bits.set(i, true);
      }
    }
    return bits;
  }
  
  public BVector(Boolean[] bits) {
    length = bits.length;
    this.bits = toBitSet(bits);
  }
  
  public BVector(BitSet bits) {
    bits.length();
    this.bits = bits;
  }
  
  public BVector(boolean... bits) {
    length = bits.length;
    this.bits = toBitSet(bits);
  }

  public int a(BVector v) {
    BitSet clone = (BitSet) bits.clone();
    clone.and(v.bits);
    return clone.cardinality();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BVector == false) {
      return false;
    }
    BVector v = (BVector) obj;
    if (bits.length() != v.bits.length()) {
      return false;
    }
    return bits.equals(v.bits);
  }

  public int d(BVector v) {
    BitSet clone = (BitSet) bits.clone();
    clone.or(v.bits);
    return length - clone.cardinality();
  }
  
  public int length() {
    return length;
  }

  public int b(BVector v) {
    BitSet clone = (BitSet) bits.clone();
    clone.or(v.bits);    
    return clone.cardinality() - v.bits.cardinality();
  }
  
  public int c(BVector v) {
    BitSet clone = (BitSet) bits.clone();
    clone.or(v.bits);    
    return clone.cardinality() - bits.cardinality();
  }
  
  @Override
  public double component(int index) {
    if (bits.get(index) == true) {
      return 1;
    } else {
      return 0;
    }
  }
  
  @Override
  public double max() {
    return 1;
  }
  
  @Override
  public double min() {
    return 0;
  }
  
  @Override
  public DitoVector rescale(double multiplier) {
    // no need to rescale a binary vector
    return this;
  }
  
  @Override
  public String toString() {
    return bits.toString();
  }
  
  @Override
  public Object genericComponent(int index) {
    return bits.get(index);
  }
}*/
