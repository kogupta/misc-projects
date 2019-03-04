package org.kogu.sedgewick_coursera.sort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.sort.SortFunctions.*;

public enum ShellExample {
  ;

  public static void sort(int[] xs) {
    int n = xs.length;

    // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ...
    int h = 1;
    while (h < n / 3) h = 3 * h + 1;

    while (h >= 1) {
      // h-sort the array
      for (int i = h; i < n; i++) {
        for (int j = i; j >= h && less(xs, j, j - h); j -= h) {
          swap(xs, j, j - h);
        }
      }
      assert isHsorted(xs, h);
      h /= 3;
    }

    assertSorted(xs);
  }

  // is the array h-sorted?
  private static boolean isHsorted(int[] a, int h) {
    for (int i = h; i < a.length; i++)
      if (a[i] < a[i - h]) return false;
    return true;
  }

  public static void main(String[] args) {
    test(10);
    test(100);
    test(1_000);
    test(10_000);
    test(100_000);
    test(1_000_000);
    test(10_000_000);
    test(100_000_000);
  }

  private static void test(int size) {
    int[] xs = new int[size];
    Arrays.setAll(xs, i -> i);
    System.out.printf("already sorted [%,d]: ", size);
    time(() -> sort(xs));

    StdRandom.shuffle(xs);
    System.out.print("unsorted: ");
    time(() -> sort(xs));

    System.out.println();
    System.gc();
  }
}
