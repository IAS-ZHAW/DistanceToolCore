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
