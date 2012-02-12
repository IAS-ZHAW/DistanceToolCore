/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito;

import net.jcip.annotations.Immutable;

@Immutable
public enum QuestionType {
  METRIC, 
  ORDINAL, 
  NOMINAL, 
  BINARY;
}
