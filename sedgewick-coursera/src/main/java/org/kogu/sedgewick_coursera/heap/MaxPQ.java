package org.kogu.sedgewick_coursera.heap;

import org.kogu.sedgewick_coursera.CommonFunctions;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.CommonFunctions.assertionStatus;
import static org.kogu.sedgewick_coursera.heap.HeapFunctions.sortDescending;
import static org.kogu.sedgewick_coursera.heap.HeapFunctions.swap;
import static org.kogu.sedgewick_coursera.CommonFunctions.createShuffledArray;

public class MaxPQ {
  private final int defaultValue;
  private int[] arr;
  private int size;

  public MaxPQ(int defaultValue) {
    this.defaultValue = defaultValue;
    arr = new int[16];
    Arrays.fill(arr, defaultValue);
  }

  void insert(int n) {
    if (n == defaultValue) throw new IllegalArgumentException("invalid value");

    if (size == arr.length - 1) expand();

    arr[++size] = n;
    swim(size);
  }

  private void expand() {
    int[] newArr = new int[3 * size / 2];
    System.arraycopy(arr, 0, newArr, 0, size + 1);
    Arrays.fill(newArr, size + 1, newArr.length - 1, defaultValue);
    arr = newArr;
  }

  boolean isEmpty() { return size == 0;}

  int max() {
    if (size == 0) throw new IllegalStateException("empty heap!!");
    return arr[1];
  }

  int delMax() {
    if (size == 0) throw new IllegalStateException("empty heap!!");

    int result = arr[1];
    arr[1] = arr[size];
    arr[size--] = defaultValue;
    sink(1);

    return result;
  }

  int size() { return size;}

  private void sink(int k) {
    while (2 * k <= size) {
      // find child
      // child indices are => 2k, 2k + 1
      int j = 2 * k;
      if (j < size && less(arr, j, j + 1)) j++;

      // is less than bigger child
      if (!less(arr, k, j)) break;

      swap(arr, k, j);
      k = j;
    }
  }

  private void swim(int k) {
    while (k > 1 && less(arr, k / 2, k)) {
      swap(arr, k / 2, k);
      k = k / 2;
    }
  }

  private static boolean less(int[] arr, int i, int j) {
    return arr[i] < arr[j];
  }

  public static void main(String[] args) {
    assertionStatus();
    test(20);
  }

  private static void test(int maxSize) {
    int[] createShuffledArray = createShuffledArray(maxSize);
    MaxPQ pq = new MaxPQ(-1);
    for (int i = 0; i < createShuffledArray.length; i++) {
      int n = createShuffledArray[i];
      pq.insert(n);
      assert pq.size() == i + 1;
    }

    assert pq.size() == maxSize;

    int[] obtained = new int[maxSize];
    int idx = 0;
    while (!pq.isEmpty())
      obtained[idx++] = pq.delMax();

    sortDescending(createShuffledArray);

    assert Arrays.equals(createShuffledArray, obtained);
  }
}
