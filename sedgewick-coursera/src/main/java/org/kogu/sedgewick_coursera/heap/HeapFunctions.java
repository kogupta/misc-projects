package org.kogu.sedgewick_coursera.heap;

import java.util.Arrays;

public final class HeapFunctions {
  private HeapFunctions() {}

  // reverse the array in place
  private static void reverse(int[] arr) {
    for (int i = 0, end = arr.length - 1; i < arr.length / 2; i++) {
      swap(arr, i, end - i);
    }
  }

  public static void sortDescending(int[] arr) {
    Arrays.sort(arr);
    reverse(arr);
  }

  static void swap(int[] arr, int i, int j) {
    int t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  public static void main(String[] args) {
    test(new int[]{});
    test(new int[]{1});
    test(new int[]{1, 2});
    test(new int[]{1, 2, 3});
    test(new int[]{1, 2, 3, 4});
  }

  private static void test(int[] xs) {
    reverse(xs);
    System.out.println(Arrays.toString(xs));
  }
}
