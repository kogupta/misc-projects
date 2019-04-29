package interview.epi.searching;

import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

import static interview.epi.array.SortFunctions.assertionStatus;
import static interview.epi.array.SortFunctions.shuffle;

public final class KthLargestElement {
  public static void main(String[] args) {
    assertionStatus();

    int[] xs = new int[25];
    Arrays.setAll(xs, i -> i);
    int[] copy = Arrays.copyOf(xs, xs.length);

    for (int k = 1; k <= xs.length; k++) {
      shuffle(xs);

      assert kthLargest(xs, k) == copy[xs.length - k];
      System.out.println("--------------");
    }
  }

  private static int kthLargest(int[] xs, int k) {
    int lo = 0, hi = xs.length - 1;
    Random r = new Random(31_01_2010);

    while (lo <= hi) {
      int pivot = lo + r.nextInt(hi - lo + 1);
      int newPivot = partition(xs, pivot, lo, hi);
      // ... more ... | pivot | ... less ...

      if (newPivot == k - 1) return xs[newPivot];
      else if (newPivot < k - 1) lo = newPivot + 1;
      else hi = newPivot - 1;
    }

    return -1;
  }

  private static int partition(int[] xs, int pivotIndex, int lo, int hi) {
    int v = xs[pivotIndex];
    int newPivotIdx = lo;
    swap(xs, pivotIndex, hi);
    for (int i = lo; i < hi; i++) {
      if (xs[i] > v) {
        swap(xs, i, newPivotIdx++);
      }
    }

    swap(xs, hi, newPivotIdx);
    System.out.printf("pivot: xs[%d] -> %d%n", newPivotIdx, xs[newPivotIdx]);
    print(xs, lo, hi);
    return newPivotIdx;
  }

  private static void print(int[] xs, int lo, int hi) {
    StringJoiner sb = new StringJoiner(", ", "[", "]");
    for (int idx = lo; idx <= hi; idx++) {
      int x = xs[idx];
      sb.add(Integer.toString(x));
    }
    System.out.println(sb.toString());
  }

  private static void swap(int[] xs, int i, int j) {
    int t = xs[j];
    xs[j] = xs[i];
    xs[i] = t;
  }
}
