package interview.epi.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static interview.epi.array.SortFunctions.assertionStatus;

/**
 * Key idea: max one can jump from index `i` is {@code A[i] + i}.
 * <br>
 *   keep track of max reachable index till now;
 * <br>
 *   update it only if max reachable from current index > max tracked so far.
 *
 */
public final class ArrayAdvance {
  public static void main(String[] args) {
    assertionStatus();

    int[] arr = {2, 4, 1, 1, 0, 2, 3};
    test(arr, true);

    arr = new int[]{3,3,1,0,2,0,1};
    test(arr, true);

    arr = new int[]{3,2,0,0,2,0,1};
    test(arr, false);
  }

  private static void test(int[] arr, boolean shouldReachEnd) {
    boolean maybe = canReachEnd(arr);
    assert maybe == shouldReachEnd;

    List<Node> path = pathToEnd(arr);
    int jumps = minJumpsToEnd(arr);
    if (maybe) {
      assert path.size() == jumps;
    } else {
      assert path.size() == 0;
      assert jumps == -1;
    }

    System.out.println("---------------------------------");
  }

  private static boolean canReachEnd(int[] arr) {
    int maxIdx = 0, lastIdx = arr.length - 1;

    System.out.println(Arrays.toString(arr));
    System.out.println("target index to reach: " + lastIdx);

    for (int i = 0; i <= maxIdx && maxIdx < lastIdx; i++) {
      int j = arr[i];

      int maxReachable = i + j; // max reachable from current index
      System.out.printf("max: %d, curr idx: %d, curr value: %d, reachable: %d%n",
          maxIdx, i, j, maxReachable);

      maxIdx = Math.max(maxIdx, maxReachable);
    }

    return maxIdx >= lastIdx;
  }

  private static List<Node> pathToEnd(int[] arr) {
    int maxIdx = 0, lastIdx = arr.length - 1;

    List<Node> jumps = new ArrayList<>(arr.length);

//    System.out.println(Arrays.toString(arr));
//    System.out.println("target index to reach: " + lastIdx);

    for (int i = 0; i <= maxIdx && maxIdx < lastIdx; i++) {
      int j = arr[i];

      int maxReachable = i + j;
//      System.out.printf("max: %d, curr idx: %d, curr value: %d, reachable: %d%n",
//          maxIdx, i, j, maxReachable);

      if (maxReachable > maxIdx) {
        maxIdx = maxReachable;
        jumps.add(new Node(i, j));
      }
    }

    System.out.println("Path: " + jumps);

    return maxIdx >= lastIdx ? jumps: List.of();
  }

  private static int minJumpsToEnd(int[] arr) {
    int maxIdx = 0, lastIdx = arr.length - 1;

    int result = 0;

//    System.out.println(Arrays.toString(arr));
//    System.out.println("target index to reach: " + lastIdx);

    for (int i = 0; i <= maxIdx && maxIdx < lastIdx; i++) {
      int j = arr[i];

      int maxReachable = i + j;
//      System.out.printf("max: %d, curr idx: %d, curr value: %d, reachable: %d%n",
//          maxIdx, i, j, maxReachable);

      if (maxReachable > maxIdx) {
        maxIdx = maxReachable;
        result++;
      }
    }

    return maxIdx >= lastIdx ? result: -1;
  }

  private static final class Node {
    final int index;
    final int value;

    public Node(int index, int value) {
      this.index = index;
      this.value = value;
    }

    @Override
    public String toString() {
      return index + "->" + value;
    }
  }
}
