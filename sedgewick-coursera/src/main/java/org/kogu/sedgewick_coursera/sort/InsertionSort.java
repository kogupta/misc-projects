package org.kogu.sedgewick_coursera.sort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.sort.SortFunctions.*;

public class InsertionSort {
  // in ith iteration:
  // array looks like:
  // [ -- sorted array -- | INDEX | ~~ no yet seen ~~ ]
  //
  // take next unseen element -
  // put it in its correct place on left side
  // so that left side remains sorted

  public static <T extends Comparable<T>> void sort(T[] xs) {
    for (int i = 0; i < xs.length; i++) {
      for (int j = i; j > 0; j--) {
        if (less(xs, j, j - 1)) {
          swap(xs, j, j - 1);
        } else {
          break;
        }
      }

    }

    assertSorted(xs);
  }

  public static void sort(int[] xs) {
//    for (int i = 0; i < xs.length; i++) {
//      for (int j = i; j > 0; j--) {
//        if (less(xs, j, j-1))
//          swap(xs, j, j - 1);
//        else
//          break;
//      }
//    }
//
//    assertSorted(xs);

    sort(xs, 0, xs.length - 1);
  }

  public static void sort(int[] xs, int from, int to) {
    for (int i = from; i <= to; i++) {
      for (int j = i; j > from; j--) {
        if (less(xs, j, j-1))
          swap(xs, j, j - 1);
        else
          break;
      }
    }

//    assertSorted(xs, from, to);
  }

  public static void main(String[] args) {
    test(10);
    test(100);
    test(1_000);
    test(10_000);
    test(100_000);
//    test(1_000_000); -> dont bother

    int[] xs = new int[20];
    Arrays.setAll(xs, i -> i);
    StdRandom.shuffle(xs);
    System.out.println(Arrays.toString(xs));
    sort(xs, 0, 9);
    System.out.println(Arrays.toString(xs));
    sort(xs, 10, 19);
    System.out.println(Arrays.toString(xs));
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
