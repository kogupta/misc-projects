package org.kogu.sedgewick_coursera.sort;

import org.kogu.sedgewick_coursera.CommonFunctions;

import java.util.Arrays;

public class PartitioningHelper {
  private PartitioningHelper() {}

  private static int partition(int[] xs, int lo, int hi) {
    int i = lo, j = hi + 1;
    while (true) {
      // find item on left to swap
      while (xs[++i] < xs[lo])
        if (i == hi) break;

      // find item on right to swap
      while (xs[lo] < xs[--j])
        if (j == lo) break;

      // check if pointers cross
      if (i >= j) break;

      System.out.printf("swapping %d <-> %d%n", i, j);
      CommonFunctions.swap(xs, i, j);
      display(xs);
    }

    CommonFunctions.swap(xs, lo, j);
    return j;
  }

  public static void main(String[] args) {
    int[] xs = CommonFunctions.createShuffledArray(10);

    System.out.println("--- before ---");
    display(xs);

    int idx = partition(xs, 0, xs.length - 1);
    System.out.println("partition index: " + idx);
    System.out.println("--- after ---");
    display(xs);
  }

  private static void display(int[] xs) {
    System.out.println(Arrays.toString(xs));
  }
}
