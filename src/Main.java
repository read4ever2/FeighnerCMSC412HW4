/*
  Filename: BankerTemplate.java
  Author: Will Feighner
  Date: 2021 11 14
  Purpose: Implements the banker's algorithm from a properly
  formatted text file of resources and processes.
*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  // static String filepath = "/Users/willfeighner/IdeaProjects/FeighnerCMSC412HW4/src/TestFile.csv";

  private static int resourceCount;
  private static int processCount;
  private static int[] totalResources;
  private static int[][] allocated;
  private static int[][] maxRequest;
  private static int[][] needResources;
  private static int total = 0;


  public static void main(String[] args) {
    System.out.print("Input file name: ");

    String filepath = System.getProperty("user.dir");


    Scanner scanner = new Scanner(System.in);  // Create a Scanner object

    String filename = scanner.nextLine();  // Read user input

    filepath = filepath.concat("/src/").concat(filename);

    CSVLoader loader = new CSVLoader(filepath);

    int[][] bankersArray = loader.getValues();
    resourceCount = bankersArray[0].length;
    processCount = (bankersArray.length - 1) / 2;
    totalResources = bankersArray[0];
    int[] available = new int[resourceCount];
    allocated = new int[processCount][resourceCount];
    maxRequest = new int[processCount][resourceCount];
    needResources = new int[processCount][resourceCount];

    System.arraycopy(bankersArray, 1, allocated, 0, allocated.length);
    System.arraycopy(bankersArray, maxRequest.length + 1, maxRequest, 0, maxRequest.length);


    for (int i = 0; i < processCount; i++) {
      for (int j = 0; j < resourceCount; j++) {
        needResources[i][j] = maxRequest[i][j] - allocated[i][j];
      }
    }

    for (int i = 0; i < resourceCount; i++) {
      int sum = 0;
      for (int j = 0; j < processCount; j++) {
        sum += allocated[j][i];
      }
      available[i] = totalResources[i] - sum;
    }


    ArrayList<Integer> safe = new ArrayList<>();

    boolean[] visited = new boolean[processCount];

    printInputs();

    System.out.println("\nSafe sequences are:");
    safeSequence(visited, allocated, needResources, available, safe);
    System.out.println("\nThere are total " + total + " safe sequences.");
  }

  static boolean checkAvailable(int process, int[][] need, int[] available) {
    boolean flag = true;

    for (int i = 0; i < resourceCount; i++) {
      if (need[process][i] > available[i]) {
        flag = false;
        break;
      }
    }
    return flag;
  }

  static void safeSequence(boolean[] visited, int[][] allocated, int[][] need, int[] available, ArrayList<Integer> safe) {
    for (int i = 0; i < processCount; i++) {
      if (!visited[i] && checkAvailable(i, need, available)) {
        visited[i] = true;

        for (int j = 0; j < resourceCount; j++) {
          available[j] += allocated[i][j];
        }
        safe.add(i);

        safeSequence(visited, allocated, need, available, safe);
        safe.remove(safe.size() - 1);

        visited[i] = false;

        for (int j = 0; j < resourceCount; j++) {
          available[j] -= allocated[i][j];
        }
      }
    }
    if (safe.size() == processCount) {
      total++;
      for (int i = 0; i < processCount; i++) {
        System.out.print("P" + (safe.get(i) + 1));
        if (i != (processCount - 1)) {
          System.out.print(", ");
        }
      }
      System.out.println();
    }
  }

  private static void printInputs() {
    System.out.println("Total Resources");
    char label = 'A';
    for (int i = 0; i < totalResources.length; i++) {
      System.out.print(Character.toString(label + i) + "\t");
    }
    System.out.println();
    for (int totalResource : totalResources) {
      System.out.print(totalResource + "\t");
    }
    System.out.println("\n");

    printArray("Allocated", allocated);
    printArray("Max", maxRequest);
    printArray("Need", needResources);
  }

  private static void printArray(String name, int[][] suppliedArray) {
    System.out.println("\t" + name + " Resources");
    char label = 'A';
    for (int i = 0; i < suppliedArray[0].length; i++) {
      System.out.print("\t" + Character.toString(label + i));
    }
    System.out.println();
    for (int i = 0; i < suppliedArray.length; i++) {
      System.out.print("P" + (i + 1));
      for (int j = 0; j < suppliedArray[0].length; j++) {
        System.out.print("\t" + suppliedArray[i][j]);
      }
      System.out.println();
    }
  }

  public static class CSVLoader {

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
}
