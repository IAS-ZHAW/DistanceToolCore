/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.config;

public interface PropertyListener {

  public void propertyChanged(ConfigProperty prop);

  public boolean listensTo(ConfigProperty prop);

}
