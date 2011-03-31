package ch.zhaw.ias.dito.config;

public interface PropertyListener {

  public void propertyChanged(ConfigProperty prop);

  public boolean listensTo(ConfigProperty prop);

}
