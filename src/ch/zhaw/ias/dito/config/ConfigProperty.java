/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
package ch.zhaw.ias.dito.config;

import net.jcip.annotations.Immutable;

@Immutable
public enum ConfigProperty {
  INPUT_FILENAME("s1.lb.file"),
  METHOD_NAME("s3.lb.method"),
  RANDOM_SAMPLE("s3.lb.sampleSize"),
  OUTPUT_FILENAME("s5.lb.file"),
  OUTPUT_PRECISION("s5.lb.precision"),
  QUESTION_NUMBER("misc.lb.questionNumber"),
  INPUT_SIZE("misc.lb.inputSize");
  
  private final String key;
  
  private ConfigProperty(String key) {
    this.key = key;
  }
  
  public String getKey() {
    return key;
  }
}
