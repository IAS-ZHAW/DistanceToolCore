/* released under bsd licence
 * see LICENCE file or http://www.opensource.org/licenses/bsd-license.php for details
 * Institute of Applied Simulation (ZHAW)
 * Author Thomas Niederberger
 */
import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumberFormatTest {
  public static void main(String... args) {
    
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(4);
    nf.setGroupingUsed(false);
    nf.setRoundingMode(RoundingMode.HALF_UP);
    long start = System.currentTimeMillis();
    for (int i = 0; i < 10000*1000; i++) {
        nf.format((double) i);
    }
    System.out.println("finished after " + (System.currentTimeMillis() - start) + " ms");
  }
}
