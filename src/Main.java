/*
  Filename: Main.java
  Author: Will Feighner
  Date: 2021 11 14
  Purpose: Implements the banker's algorithm from a properly
  formatted text file of resources and processes.
*/


public class Main {

  static String filepath = "/Users/willfeighner/IdeaProjects/FeighnerCMSC412HW4/src/TestFile.csv";

  public static void main(String[] args) {
    CSVLoader loader = new CSVLoader(filepath);
    int[][] bankersArray = loader.getValues();
    int resourceCount = bankersArray[0].length;
    int processCount = (bankersArray.length - 1) / 2;
    int[] resources = bankersArray[0];
    int[] available = new int[resourceCount];
    int[][] allocated = new int[processCount][resourceCount];
    int[][] maxRequest = new int[processCount][resourceCount];

    System.arraycopy(bankersArray, 1, allocated, 0, allocated.length);
    System.arraycopy(bankersArray, maxRequest.length + 1, maxRequest, 0, maxRequest.length);

    for (int i = 0; i < resourceCount; i++) {
      int total = 0;
      for (int j = 0; j < processCount; j++) {
        total += allocated[j][i];
      }
      available[i] = resources[i] - total;
      System.out.print(available[i] + ", ");
    }
    System.out.println();

    for (int[] ints : bankersArray) {
      for (int j = 0; j < resourceCount; j++) {
        System.out.print(ints[j] + ", ");
      }
      System.out.println();
    }

  }
}
