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

import ch.zhaw.ias.dito.config.ImportWrapper;

public class GenerateTagMatrix {

  public static int MIN_COUNT = 3;
  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String filename = "C:/Daten/atizo/tag-file-pro-128.csv";
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    Pattern pattern = Pattern.compile(",\\s*");
    String line;
    Map<String, Pair> tagList = new HashMap<String, Pair>();
    List<List<Integer>> vectors = new ArrayList<List<Integer>>();
    List<String> tagStopList = new ArrayList<String>();
    // Project 128
    Collections.addAll(tagStopList, "bad", "spiegel");
    // Project 638
    //Collections.addAll(tagStopList, "konfi", "konfitüre", "marmelade");

    int lineCounter = 0;
    int tagCounter = 0;
    while ((line = reader.readLine()) != null) {
      Scanner s = new Scanner(line);
      s.useDelimiter(pattern);
      int rowCounter = 0;
      
      ArrayList<Integer> values = new ArrayList<Integer>();
      while (s.hasNext()) {
        String tag = s.next();
        //ignore tags from stop-list
        if (tagStopList.contains(tag)) {
          continue;
        }
        if (! tagList.containsKey(tag)) {
          tagList.put(tag, new Pair(tagCounter++));
        }
        Pair p = tagList.get(tag);
        p.count = p.count + 1;
        values.add(p.index);
        
        rowCounter++;
      }
      lineCounter++;
      vectors.add(values);
    }
    System.out.println("lines: " + lineCounter);
    System.out.println("tagCounter: " + tagCounter);
        
    filename = "C:/Daten/atizo/tag-file-out-pro-128.csv";
    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    
    //write headers
    for (Iterator<String> it = tagList.keySet().iterator(); it.hasNext(); ) {
      //for (int j = 0; j < tagCounter; j++) {
        String key = it.next();
        if (tagList.get(key).count < MIN_COUNT) {
          continue;
        }
        System.out.println(key + ": " + tagList.get(key).count);
        writer.write(key + ";");
    }
    writer.newLine();
    
    for (int i = 0; i < vectors.size(); i++) {
      List<Integer> matrixLine = vectors.get(i);
      for (Iterator<String> it = tagList.keySet().iterator(); it.hasNext(); ) {
      //for (int j = 0; j < tagCounter; j++) {
        String key = it.next();
        if (tagList.get(key).count < MIN_COUNT) {
          continue;
        }
        
        if (matrixLine.contains(tagList.get(key).index)) {
          writer.write("1;");
        } else {
          writer.write("0;");
        }
      }
      writer.newLine();
    }
    writer.close();
  }
  
  static class Pair {
    int index = 0;
    int count = 0;
    
    public Pair(int index) {
      this.index = index;
    }
  }
}
