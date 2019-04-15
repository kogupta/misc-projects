package interview;

import java.util.Arrays;
import java.util.Random;

// reference: https://www.geeksforgeeks.org/median-of-two-sorted-arrays-of-different-sizes/
// extension of "merge" phase of merge-sort
// given 2 sorted arrays, keep track of median element
// Another elegant solution:
// reference: https://www.youtube.com/watch?v=MHNTl_NvOj0
// progressively prune the 2 sorted arrays, to come with smaller candidate medians
public final class Median2SortedArrays2 {
  private static final Random rnd = new Random(31_01_2010);

  public static void main(String[] args) {
    int[] inputSizes = new int[10];
    Arrays.setAll(inputSizes, i -> 10 + i);
    for (int n : inputSizes) {
      Triple triple = generate(n);
      double median = findMedian(triple.xs, triple.ys);
      assert triple.expectedMedian == median :
          String.format("Expected: %.2f, got: %.2f", triple.expectedMedian, median);

      System.out.println("-----------------------");
    }
  }

  private static double findMedian(int[] xs, int[] ys) {
    int totalLen = xs.length + ys.length;
    int medianIdx = totalLen / 2;
    int m1 = -1, m2 = -1;
    for (int i = 0, j = 0, k = 0;
         i < xs.length && j < ys.length && k <= medianIdx;
         k++) {
      m2 = m1;
      int x = xs[i], y = ys[j];
      if (x <= y) i++;
      else j++;
      m1 = Math.min(x, y);
    }

    return totalLen % 2 == 1 ? m1 : (m1 + m2) / 2.0;
  }

  private static Triple generate(int n) {
    int[] xs = new int[n];
    Arrays.setAll(xs, i -> rnd.nextInt(10));
    Arrays.sort(xs);
    double median = n % 2 == 0 ? ((double) xs[n / 2] + xs[n / 2 - 1]) / 2 : xs[n / 2];
    int[] left = new int[n / 2], right = new int[n - n / 2];
    for (int i = 0, len = xs.length; i < len; i++) {
      int[] target = i % 2 == 0 ? right : left;
      target[i / 2] = xs[i];
    }

    System.out.println(Arrays.toString(xs));
    System.out.println(Arrays.toString(left));
    System.out.println(Arrays.toString(right));
    System.out.printf("%.2f%n", median);

    return new Triple(left, right, median);
  }

  private static final class Triple {
    final int[] xs, ys;
    final double expectedMedian;

    private Triple(int[] xs, int[] ys, double median) {
      this.xs = xs;
      this.ys = ys;
      this.expectedMedian = median;
    }
  }
}
