/*
  Filename: Main.java
  Author: Will Feighner
  Date: 2021 11 14
  Purpose: Implements the banker's algorithm from a properly
  formatted text file of resources and processes.
*/


public class Main {
  public static void main(String[] args) {
    CSVLoader loader = new CSVLoader("/Users/willfeighner/IdeaProjects/FeighnerCMSC412HW4/src/TestFile.csv");
    int[][] bankersArray = loader.getValues();
    int[] resources = bankersArray[0];
    int[][] allocated = new int[bankersArray.length][(bankersArray[0].length - 1) / 2];
    int[][] maxRequest = new int[bankersArray.length][(bankersArray[0].length - 1) / 2];

    for (int[] ints : bankersArray) {
      for (int j = 0; j < bankersArray[0].length; j++) {
        System.out.print(ints[j] + ", ");
      }
      System.out.println();
    }
  }
}
