package org.kogu.sedgewick_coursera.sort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.sort.SortFunctions.*;

public class SelectionSort {
  // in ith iteration:
  // array looks like:
  // [ -- sorted array -- | INDEX | ~~ unsorted ~~ ]
  // find the smallest item to RIGHT of sorted array
  // put it at index

  public static <T extends Comparable<T>> void sort(T[] xs) {
    int end = xs.length;
    for (int i = 0; i < end; i++) {
      int minIndex = i;
      for (int j = i + 1; j < end; j++) {
        if (less(xs, j, minIndex))
          minIndex = j;
      }

      swap(xs, i, minIndex);
    }

    assertSorted(xs);
  }

  public static void sort(int[] xs) {
    int end = xs.length;
    for (int i = 0; i < end; i++) {
      int minIndex = i;
      for (int j = i + 1; j < end; j++) {
        if (less(xs, j, minIndex))
          minIndex = j;
      }

      swap(xs, i, minIndex);
    }

    assertSorted(xs);
  }

  public static void main(String[] args) {
    test(10);
    test(100);
    test(1_000);
    test(10_000);
    test(100_000);
//    test(1_000_000); -> dont bother
  }

  private static void test(int size) {
    int[] xs = new int[size];
    Arrays.setAll(xs, i -> i);
    System.out.printf("already sorted [%,d]: ", size);
    time(() -> sort(xs));
    assertSorted(xs);

    StdRandom.shuffle(xs);
    System.out.print("unsorted: ");
    time(() -> sort(xs));
    assertSorted(xs);

    System.out.println();
  }


}
