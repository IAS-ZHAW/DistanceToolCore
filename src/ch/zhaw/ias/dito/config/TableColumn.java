package ch.zhaw.ias.dito.config;

public enum TableColumn {
  NUMBER(0, false),
  NAME(1, true),
  VALUES(2, false),
  MIN(3, false),
  MAX(4, false),
  TYPE(5, false),
  DISTANCE_WEIGHT(6, true),
  QUESTION_WEIGHT(7, true),
  SCALING(8, true);
  
  public static TableColumn getById(int id) {
    for (TableColumn c : values()) {
      if (c.getId() == id) {
        return c;
      }
    }
    return null;
  }
  
  private int id;
  private boolean editable;
  
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