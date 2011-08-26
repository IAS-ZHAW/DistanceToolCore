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

public class GenerateTagMatrix {

  public static int MIN_COUNT = 3;
  public static int PROJECT_ID = 128;
  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String filename = "C:/Daten/atizo/tag-file-pro-" + PROJECT_ID + ".csv";
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    Pattern pattern = Pattern.compile("[,]\\s*");
    String line;
    Map<String, Pair> tagList = new HashMap<String, Pair>();
    List<List<Integer>> vectors = new ArrayList<List<Integer>>();
    
    Map<Integer, List<String>> tagStopContainer = new HashMap<Integer, List<String>>();
    // Project 128
    tagStopContainer.put(128, new ArrayList<String>());
    //Collections.addAll(tagStopContainer.get(128), "nicht", "zu", "mit", "fuer", "bade", "bad", "spiegel");
    // Project 638
    tagStopContainer.put(638, new ArrayList<String>());
    Collections.addAll(tagStopContainer.get(638), "konfi", "konfituere", "marmelade", "confiture");
    // Project 804
    tagStopContainer.put(804, new ArrayList<String>());
    Collections.addAll(tagStopContainer.get(804), "mit", "fuer");
    
    List<String> tagStopList = tagStopContainer.get(PROJECT_ID);
    
    int lineCounter = 0;
    int tagCounter = 0;
    while ((line = reader.readLine()) != null) {
      Scanner s = new Scanner(line);
      s.useDelimiter(pattern);
      int rowCounter = 0;
      
      ArrayList<Integer> values = new ArrayList<Integer>();
      while (s.hasNext()) {
        String tag = s.next();
        tag = standardsizeTag(tag, tagStopList);
        //ignore tags from stop-list
        if (tag.length() == 0) {
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
        
    filename = "C:/Daten/atizo/tag-file-out-pro-" + PROJECT_ID + ".csv";
    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    
    int countedTags = 0; 
    //write headers
    for (Iterator<String> it = tagList.keySet().iterator(); it.hasNext(); ) {
        String key = it.next();
        if (tagList.get(key).count < MIN_COUNT) {
          continue;
        }
        System.out.println(key + ": " + tagList.get(key).count);
        writer.write(key + ";");
        countedTags++;
    }
    writer.newLine();
    System.out.println("records: " + lineCounter);
    System.out.println("tagCounter: " + tagCounter);
    System.out.println("Tags with " + MIN_COUNT + " mentions at least: " + countedTags);
    
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
  
  private static String standardsizeTag(String tag, List<String> tagStopList) {
    String[][] replacements = {{"ß", "ä", "ö", "ü", "é", "è", "à"}, {"ss", "ae", "oe", "ue", "e", "e", "a"}};
    /*for (int i = 0; i < replacements[0].length; i++) {
      tag = tag.replace(replacements[0][i], replacements[1][i]);
    }
    for (int i = 0; i < tagStopList.size(); i++) {
      tag = tag.replace(tagStopList.get(i), "");
    }*/
    return tag;
  }

  static class Pair {
    int index = 0;
    int count = 0;
    
    public Pair(int index) {
      this.index = index;
    }
  }
}
