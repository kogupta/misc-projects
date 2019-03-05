package org.kogu.sedgewick_coursera.sort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.CommonFunctions.time;
import static org.kogu.sedgewick_coursera.sort.MergeHelper.merge2;

public class MergeSortTest {
  private static final int threshold = 32;

  private MergeSortTest() {}

  public static void sort(int[] arr) {
    int[] aux = new int[arr.length];
    sort(arr, aux, 0, arr.length - 1);
  }

  private static void sort(int[] arr, int[] aux, int lo, int hi) {
    if (hi - lo + 1 < threshold) {
      InsertionSort.sort(arr, lo, hi);
      return;
    }

    int mid = lo + (hi - lo) / 2;
    sort(arr, aux, lo, mid);
    sort(arr, aux, mid + 1, hi);

    // it is already sorted - do nothing
    if (arr[mid] <= arr[mid + 1]) return;

//    merge(arr, aux, lo, mid, hi);
    merge2(arr, aux, lo, mid, hi);
  }

  public static void main(String[] args) {
    test(10);
    test2(10);
    test3(10);
    test(100);
    test2(100);
    test3(100);
    test(1_000);
    test2(1_000);
    test3(1_000);
    test(10_000);
    test2(10_000);
    test3(10_000);
    test(100_000);
    test2(100_000);
    test3(100_000);
    test(1_000_000);
    test2(1_000_000);
    test3(1_000_000);
    test(10_000_000);
    test2(10_000_000);
    test3(10_000_000);
    test(100_000_000);
    test2(100_000_000);
    test3(100_000_000);
  }

  private static void test(int size) {
    int[] xs = new int[size];
    Arrays.setAll(xs, i -> i);
    System.out.printf("already sorted [%,d]: ", size);
    time(() -> sort(xs));
    System.gc();

    StdRandom.shuffle(xs);
    System.out.print("unsorted: ");
    time(() -> sort(xs));

    System.gc();
  }

  private static void test2(int n) {
    Integer[] xs = new Integer[n];
    Arrays.setAll(xs, i -> i);
    System.out.printf("<boxed> already sorted [%,d]: ", n);
//    time(() -> MergeX.sort(xs));
    time(() -> Arrays.sort(xs));
    System.gc();

    StdRandom.shuffle(xs);
    System.out.print("<boxed> unsorted: ");
//    time(() -> MergeX.sort(xs));
    time(() -> Arrays.sort(xs));

    System.gc();
  }

  private static void test3(int n) {
    int[] xs = new int[n];
    Arrays.setAll(xs, i -> i);
    System.out.printf("<jdk quicksort> already sorted [%,d]: ", n);
    time(() -> Arrays.sort(xs));
    System.gc();

    StdRandom.shuffle(xs);
    System.out.print("<jdk quicksort> unsorted: ");
    time(() -> Arrays.sort(xs));

    System.out.println();
    System.gc();
  }
}
