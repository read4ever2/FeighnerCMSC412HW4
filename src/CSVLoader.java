/*
 *  Filename: BankerTemplate.java
 *  Author: Will Feighner
 *  Date: 2021 11 14
 * Purpose: Implements the banker's algorithm from a properly
 * formatted text file of resources and processes.
 *
 * */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader {

  public final String filepath;

  CSVLoader(String filepath) {
    this.filepath = filepath;
  }

  public int[][] getValues() {
    int[][] matrix = new int[0][1];
    String line;
    String[] tokens;
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

      int resources = Integer.parseInt(bufferedReader.readLine().split(",")[1]);
      int processes = Integer.parseInt(bufferedReader.readLine().split(",")[1]);

      int j = 0;
      matrix = new int[(processes * 2) + 1][resources];
      while ((line = bufferedReader.readLine()) != null) {
        tokens = line.split(",");
        for (int i = 0; i < tokens.length - 1; i++) {
          matrix[j][i] = Integer.parseInt(tokens[i + 1]);
        }
        j++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return matrix;
  }
}
