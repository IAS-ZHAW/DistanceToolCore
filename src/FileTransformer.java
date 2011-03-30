import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FileTransformer {
  public static void main(String... args) {
        
    //String str = "10.0541,10.0541,10.0541,10.0541,10.0541,10.0541,10.0542,10.0542,10.0542,10.0542,10.0543,10.0543,10.0544,10.0545,10.0546,10.0547,10.0549,10.0552,10.0555,10.0559,10.0564,10.0571,10.0580,10.0591,10.0605,10.0623,10.0645,10.0672,10.0705,10.0747,10.0797,10.0859,10.0934,10.1024,10.1132,10.1261,10.1415,10.1597,10.1810,10.2061,10.2352,10.2690,10.3080,10.3527,10.4037,10.4617,10.5271,10.6006,10.6827,10.7740,10.8749,10.9857,11.1068,11.2384,11.3805,11.5331,11.6959,11.8686,12.0505,12.2409,12.4389,12.6433,12.8527,13.0656,13.2804,13.4952,13.7079,13.9167,14.1192,14.3134,14.4971,14.6682,14.8246,14.9644,15.0859,15.1875,15.2680,15.3263,15.3615,15.3734,15.3615,15.3263,15.2680,15.1875,15.0859,14.9644,14.8246,14.6682,14.4971,14.3134,14.1192,13.9167,13.7079,13.4952,13.2804,13.0656,12.8527,12.6433,12.4389,12.2409,12.0505,11.8686,11.6959,11.5331,11.3805,11.2384,11.1068,10.9857,10.8749,10.7740,10.6827,10.6006,10.5271,10.4617,10.4037,10.3527,10.3080,10.2690,10.2352,10.2061,10.1810,10.1597,10.1415,10.1261,10.1132,10.1024,10.0934,10.0859,10.0797,10.0747";
    String str = " ; ; ";
    str = str.replaceAll("['|\"]", "");
    char separator = ';';
    //Pattern pattern = Pattern.compile("(^(\\s|'|\")*|(\\s|'|\")*" + separator + "(\\s|'|\")*|(\\s|'|\")*$)");
    //Pattern pattern = Pattern.compile("[(^[\\s|'|\"]*)|([\\s|'|\"]*" + separator + "[\\s|'|\"]*)|([\\s|'|\"]*$)]");
    //Pattern pattern = Pattern.compile("(" + Character.toString(separator) + "{1,1}?)");
    Pattern pattern = Pattern.compile(Character.toString(separator) + "{1,1}");  
      Scanner s = new Scanner(str);
        s.useDelimiter(pattern);
        ArrayList<Double> values = new ArrayList<Double>();
        int i = 0;
          while (s.hasNext()) {
            if (s.hasNextDouble()) {
              double v= s.nextDouble();
              values.add(v);
              System.out.println(v);
              
            } else {
              String ignored = s.next();  
              values.add(Double.NaN);           
            }
            i++; 
          }
          System.out.println(i);
  }
}
