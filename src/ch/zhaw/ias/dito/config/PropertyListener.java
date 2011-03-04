package ch.zhaw.ias.dito.config;

public interface PropertyListener {

  public void propertyChanged(ConfigProperty prop, Object oldValue, Object newValue);

  public boolean listensTo(ConfigProperty prop);

}
