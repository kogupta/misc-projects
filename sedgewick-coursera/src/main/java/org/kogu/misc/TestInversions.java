package org.kogu.misc;

import org.kogu.sedgewick_coursera.CommonFunctions;
import org.kogu.sedgewick_coursera.heap.HeapFunctions;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.CommonFunctions.assertionStatus;
import static org.kogu.sedgewick_coursera.CommonFunctions.createSortedArray;
import static org.kogu.sedgewick_coursera.heap.HeapFunctions.sortDescending;

public final class TestInversions {
  private TestInversions() {}

  public static int count(int[] a) {
    if (a == null || a.length == 0 || a.length == 1) return 0;

    int[] copy = Arrays.copyOf(a, a.length);
    int[] aux = new int[a.length];

    return _count(a, copy, aux, 0, a.length - 1);
  }

  private static int _count(int[] original, int[] copy, int[] aux, int lo, int hi) {
    if (hi - lo == 0) { return 0;} // no inversions for singleton array

    int inv = 0;

    int mid = lo + (hi - lo) / 2;
    inv += _count(original, copy, aux, lo, mid);
    inv += _count(original, copy, aux, mid + 1, hi);
    // split inversions
    inv += merge(copy, aux, lo, mid, hi);
    return inv;
  }

  private static int merge(int[] a, int[] aux, int lo, int mid, int hi) {
    System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

    int i = lo, j = mid + 1, inv = 0;
    for (int k = lo; k <= hi; k++) {
      if      (i > mid) a[k] = aux[j++];
      else if (j > hi)  a[k] = aux[i++];
      else if (aux[i] <= aux[j]) a[k] = aux[i++];
      else {
        // inversion
        inv += mid - i + 1;
        a[k] = aux[j++];
      }
    }

    return inv;
  }

  public static void main(String[] args) {
    assertionStatus();
    test(2);
    test(3);
    test(4);
    test(6);
    test(10);
  }

  private static void test(int size) {
    int[] xs = createSortedArray(size);
    assert count(xs) == 0;

    sortDescending(xs);
    assert count(xs) == size * (size - 1) / 2;
  }
}
