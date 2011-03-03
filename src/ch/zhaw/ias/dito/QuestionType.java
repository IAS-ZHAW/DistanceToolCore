package ch.zhaw.ias.dito;

import net.jcip.annotations.Immutable;

@Immutable
public enum QuestionType {
  METRIC, 
  ORDINAL, 
  NOMINAL, 
  BINARY;
}
