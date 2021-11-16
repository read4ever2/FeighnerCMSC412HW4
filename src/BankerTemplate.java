import java.util.ArrayList;

public class BankerTemplate {

  // total number of process

  static int P = 7;

  // total number of resources

  static int R = 5;

  // total safe-sequences

  static int total = 0;

  // function to check if process
  // can be allocated or not

  static boolean is_available(int process_id, int[][] need, int[] available) {
    boolean flag = true;

    // check if all the available resources

    // are less great than need of process

    for (int i = 0; i < R; i++) {
      if (need[process_id][i] > available[i]) {
        flag = false;
        break;
      }
    }
    return flag;
  }

  // Print all the safe-sequences

  static void safe_sequence(boolean[] marked, int[][] allocated, int[][] need, int[] available,
                            ArrayList<Integer> safe) {
    for (int i = 0; i < P; i++) {
      // check if it is not marked
      // already and can be allocated
      if (!marked[i] && is_available(i, need, available)) {
        // mark the process
        marked[i] = true;
        // increase the available
        // by deallocating from process i
        for (int j = 0; j < R; j++) {
          available[j] += allocated[i][j];
        }
        safe.add(i);
        // find safe sequence by taking process i
        safe_sequence(marked, allocated, need, available, safe);
        safe.remove(safe.size() - 1);
        // unmark the process
        marked[i] = false;
        // decrease the available
        for (int j = 0; j < R; j++) {
          available[j] -= allocated[i][j];
        }
      }
    }
    // if a safe-sequence is found, display it
    if (safe.size() == P) {
      total++;
      for (int i = 0; i < P; i++) {
        System.out.print("P" + (safe.get(i) + 1));
        if (i != (P - 1)) {
          System.out.print(", ");
        }
      }
      System.out.println();
    }
  }

  // Driver Code
  public static void main(String[] args) {
    // allocated matrix of size P*R
    int[][] allocated = {
        {2, 2, 3, 0, 0},
        {1, 2, 3, 1, 2},
        {2, 3, 0, 2, 3},
        {1, 2, 2, 1, 1},
        {2, 2, 2, 2, 2},
        {0, 1, 1, 1, 1},
        {1, 0, 0, 3, 3}};
    // max matrix of size P*R
    int[][] max = {
        {4, 2, 5, 1, 1},
        {1, 6, 6, 2, 2},
        {7, 3, 1, 3, 6},
        {5, 6, 3, 3, 2},
        {2, 6, 6, 4, 2},
        {6, 5, 1, 4, 6},
        {4, 2, 2, 3, 6}};
    // Initial total resources
    int[] resources = {11, 12, 13, 11, 15};
    // available vector of size R
    int[] available = new int[R];
    for (int i = 0; i < R; i++) {
      int sum = 0;
      for (int j = 0; j < P; j++) {
        sum += allocated[j][i];
      }
      available[i] = resources[i] - sum;
    }

    // safe vector for displaying a safe-sequence
    ArrayList<Integer> safe = new ArrayList<>();
    // marked of size P for marking allocated process
    boolean[] marked = new boolean[P];
    // need matrix of size P*R
    int[][] need = new int[P][R];
    for (int i = 0; i < P; i++) {
      for (int j = 0; j < R; j++) {
        need[i][j] = max[i][j] - allocated[i][j];
      }
    }

    System.out.println("Safe sequences are:");
    safe_sequence(marked, allocated, need, available, safe);
    System.out.println("\nThere are total " + total + " safe-sequences");
  }
}