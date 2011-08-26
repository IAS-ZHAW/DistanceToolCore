package ch.zhaw.ias.dito.config;

import net.jcip.annotations.Immutable;

@Immutable
public enum TableColumn {
  NUMBER(0, false),
  NAME(1, true),
  VALUES(2, false),
  EXCLUDE(3, true),
  MIN(4, false),
  MAX(5, false),
  TYPE(6, false),
  DISTANCE_WEIGHT(7, true),
  QUESTION_WEIGHT(8, true),
  SCALING(9, true),
  OFFSET(10, true);
  
  public static TableColumn getById(int id) {
    for (TableColumn c : values()) {
      if (c.getId() == id) {
        return c;
      }
    }
    return null;
  }
  
  private final int id;
  private final boolean editable;
  
  private TableColumn(int id, boolean editable) {
    this.id = id;
    this.editable = editable;
  }
  
  public String getResourceKey() {
    return "s2.title." + id;
  }
  
  public boolean isEditable() {
    return editable;
  }
  
  public int getId() {
    return id;
  }
}