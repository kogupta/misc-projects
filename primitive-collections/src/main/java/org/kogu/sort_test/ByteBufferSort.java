package org.kogu.sort_test;

import java.nio.ByteBuffer;

public final class ByteBufferSort {
  private static final int QUICKSORT_NO_REC = 16;
  private static final int QUICKSORT_MEDIAN_OF_9 = 128;
  private static final int stringLength = 36;
  private static final int recordLength = stringLength + Long.BYTES;
  private static final byte[] bytes = new byte[recordLength];

  @SuppressWarnings("unchecked")
  public static <K> void quickSort(final K[] x, final int from, final int to) {
    final int len = to - from;
    // Selection sort on smallest arrays
    if (len < QUICKSORT_NO_REC) {
      selectionSort(x, from, to);
      return;
    }
    // Choose a partition element, v
    int m = from + len / 2;
    int l = from;
    int n = to - 1;
    if (len > QUICKSORT_MEDIAN_OF_9) { // Big arrays, pseudomedian of 9
      int s = len / 8;
      l = med3(x, l, l + s, l + 2 * s);
      m = med3(x, m - s, m, m + s);
      n = med3(x, n - 2 * s, n - s, n);
    }
    m = med3(x, l, m, n); // Mid-size, med of 3
    final K v = x[m];
    // Establish Invariant: v* (<v)* (>v)* v*
    int a = from, b = a, c = to - 1, d = c;
    while (true) {
      int comparison;
      while (b <= c && (comparison = (((Comparable<K>) (x[b])).compareTo(v))) <= 0) {
        if (comparison == 0)
          swap(x, a++, b);
        b++;
      }
      while (c >= b && (comparison = (((Comparable<K>) (x[c])).compareTo(v))) >= 0) {
        if (comparison == 0)
          swap(x, c, d--);
        c--;
      }
      if (b > c)
        break;
      swap(x, b++, c--);
    }
    // Swap partition elements back to middle
    int s;
    s = Math.min(a - from, b - a);
    swap(x, from, b - s, s);
    s = Math.min(d - c, to - d - 1);
    swap(x, b, to - s, s);
    // Recursively sort non-partition-elements
    if ((s = b - a) > 1)
      quickSort(x, from, from + s);
    if ((s = d - c) > 1)
      quickSort(x, to - s, to);
  }

  @SuppressWarnings("unchecked")
  private static <K> void selectionSort(final K[] a, final int from, final int to) {
    for (int i = from; i < to - 1; i++) {
      int m = i;
      for (int j = i + 1; j < to; j++)
        if ((((Comparable<K>) (a[j])).compareTo(a[m]) < 0))
          m = j;
      if (m != i) {
        final K u = a[i];
        a[i] = a[m];
        a[m] = u;
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static <K> int med3(final K x[], final int a, final int b, final int c) {
    final int ab = (((Comparable<K>) (x[a])).compareTo(x[b]));
    final int ac = (((Comparable<K>) (x[a])).compareTo(x[c]));
    final int bc = (((Comparable<K>) (x[b])).compareTo(x[c]));
    return (ab < 0 ? (bc < 0 ? b : ac < 0 ? c : a) : (bc > 0 ? b : ac > 0 ? c : a));
  }

  private static <K> void swap(final K x[], final int a, final int b) {
    final K t = x[a];
    x[a] = x[b];
    x[b] = t;
  }

  private static <K> void swap(final K[] x, int a, int b, final int n) {
    for (int i = 0; i < n; i++, a++, b++)
      swap(x, a, b);
  }

  private static int compareSection(ByteBuffer bb, int fromA, int fromB) {
    long a = bb.getLong(fromA + stringLength);
    long b = bb.getLong(fromB + stringLength);
    return Long.compare(a, b);
  }
}
