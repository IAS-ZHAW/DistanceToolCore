package ch.zhaw.ias.dito;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CountryFileSplitter {

  public static void main(String[] args) throws IOException {
    String filename = "C:/Daten/analysen/wvs/wvs-religion-nmiss1.csv";
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    BufferedWriter writer = null;
    Pattern pattern = Pattern.compile("[ ]\\s*");
    String line;
    
    Map<Integer, String> countries = new HashMap<Integer, String>();
    initalizeCountries(countries);
    
    int previousCountryId = -1;
    int columns = -1;
    int lineCounter = -1;
    int totalLines = 0;
    Integer countryId = -1;
    while ((line = reader.readLine()) != null) {
      Scanner s = new Scanner(line);
      s.useDelimiter(pattern);
      
      countryId = Integer.parseInt(s.next());
      int columnCounter = 1; // one column already read
      
      while (s.hasNext()) {
        columnCounter++;
        s.next();
      }
      //initialize columns
      if (columns == -1) {
        columns = columnCounter;
      }
      if (columns != columnCounter) {
        throw new IllegalStateException("Error on line " + lineCounter + ". Has " + columnCounter + " columns instead of " + columns);
      }
      lineCounter++;
      if (countryId != previousCountryId || previousCountryId == -1) {
        if (previousCountryId != -1) {
          writer.close();
          System.out.println("processed " + countries.get(previousCountryId) + " : " + lineCounter + " records");
          totalLines += lineCounter;
          lineCounter = 0;
        }
        
        writer = new BufferedWriter(new FileWriter("C:/Daten/analysen/wvs/country-files/wvs-" + countries.get(countryId) + ".csv", true));
        previousCountryId = countryId;
      }
      writer.write(line);
      writer.newLine();
    }
    //process last file
    lineCounter++;
    writer.close();
    System.out.println("processed " + countries.get(previousCountryId) + " : " + lineCounter + " records");
    totalLines += lineCounter;
    lineCounter = 0;
    
    System.out.println("processed " + totalLines + " records with " + columns + " columns");
  }
  
  private static void initalizeCountries(Map<Integer, String> countries) {
    countries.put(276, "Germany");
    countries.put(113, "Rwanda");
    countries.put(111, "Ethiopia");
    countries.put(109, "Malaysia");
    countries.put(105, "Trinidad and Tobago");
    countries.put(103, "Serbia and Montenegro");
    countries.put(101, "SrpSka - Serbian Republic of Bosnia");
    countries.put(98, "Guatemala");
    countries.put(96, "Algeria");
    countries.put(94, "Cuba");
    countries.put(92, "Jordan");
    countries.put(90, "Morocco");
    countries.put(88, "New Zealand");
    countries.put(86, "Luxemburg");
    countries.put(84, "Croatia");
    countries.put(82, "Montenegro");
    countries.put(80, "Valencia");
    countries.put(78, "Andalusia");
    countries.put(76, "Malta");
    countries.put(74, "Uganda");
    countries.put(72, "Albania");
    countries.put(70, "Indonesia");
    countries.put(68, "Dominican Republic");
    countries.put(66, "El Salvador");
    countries.put(64, "Azerbaijan");
    countries.put(62, "Georgia");
    countries.put(60, "Tanzania");
    countries.put(58, "Philippines");
    countries.put(56, "Ghana");
    countries.put(54, "Uruguay");
    countries.put(52, "El Salvador");
    countries.put(50, "Russia");
    countries.put(48, "Estonia");
    countries.put(46, "Lithuania");
    countries.put(44, "Turkey");
    countries.put(42, "Austria");
    countries.put(40, "Taiwan");
    countries.put(38, "Pakistan");
    countries.put(36, "Bulgaria");
    countries.put(34, "East Germany");
    countries.put(32, "India");
    countries.put(30, "Chile");
    countries.put(28, "Brazil");
    countries.put(26, "Switzerland");
    countries.put(24, "S Korea");
    countries.put(22, "Argentina");
    countries.put(20, "Tambov (Rusia)");
    countries.put(18, "Norway");
    countries.put(16, "Hungary");
    countries.put(14, "Mexico");
    countries.put(12, "Canada");
    countries.put(10, "N Ireland");
    countries.put(8, "Spain");
    countries.put(6, "Denmark");
    countries.put(4, "Italy");
    countries.put(2, "Britain");
    countries.put(-1, "Don´t know");
    countries.put(-3, "Not applicable");
    countries.put(-5, "Missing;Unknown");
    countries.put(114, "Zambia");
    countries.put(112, "Mali");
    countries.put(110, "Burkina Faso");
    countries.put(108, "Andorra");
    countries.put(104, "Hong Kong");
    countries.put(102, "Bosnia Federation");
    countries.put(99, "Kyrgyzstan");
    countries.put(97, "Iraq");
    countries.put(95, "Cyprus");
    countries.put(93, "Bosnia");
    countries.put(91, "Iran");
    countries.put(89, "Egypt");
    countries.put(87, "Sri Lanka");
    countries.put(85, "Slovakia");
    countries.put(83, "Macedonia");
    countries.put(81, "Serbia");
    countries.put(79, "Galicia");
    countries.put(77, "Singapore");
    countries.put(75, "Basque Country");
    countries.put(73, "Colombia");
    countries.put(71, "Vietnam");
    countries.put(69, "Bangladesh");
    countries.put(67, "Saudi Arabia");
    countries.put(65, "Thailand");
    countries.put(63, "Armenia");
    countries.put(61, "Moldova");
    countries.put(59, "Israel");
    countries.put(57, "Zimbabwe");
    countries.put(55, "Costa Rica");
    countries.put(53, "Venezuela");
    countries.put(51, "Peru");
    countries.put(49, "Ukraine");
    countries.put(47, "Latvia");
    countries.put(45, "Moscow");
    countries.put(43, "Greece");
    countries.put(41, "Portugal");
    countries.put(39, "China");
    countries.put(37, "Romania");
    countries.put(35, "Slovenia");
    countries.put(33, "Czech Republic");
    countries.put(31, "Belarus");
    countries.put(29, "Nigeria");
    countries.put(27, "Puerto Rico");
    countries.put(25, "Poland");
    countries.put(23, "Finland");
    countries.put(21, "Iceland");
    countries.put(19, "Sweden");
    countries.put(17, "Australia");
    countries.put(15, "S Africa");
    countries.put(13, "Japan");
    countries.put(11, "USA");
    countries.put(9, "Ireland");
    countries.put(7, "Belgium");
    countries.put(5, "Netherlands");
    countries.put(3, "West Germany");
    countries.put(1, "France");
    countries.put(-2, "No answer");
    countries.put(-4, "Not asked");
  }
}
