package org.kogu.sort_test;

import java.nio.ByteBuffer;

public final class ByteBufferSort<T extends Comparable<T>> {
  private static final int QUICKSORT_NO_REC = 16;
  private static final int QUICKSORT_MEDIAN_OF_9 = 128;

  private final ByteBuffer data;
  private final int[] index;
  private final ObjectExtractor<T> extractor;
  private final BBFixedWidthComparator comparator;
  private final int recordWidth;

  public ByteBufferSort(ByteBuffer data,
                        int[] index,
                        ObjectExtractor<T> extractor,
                        BBFixedWidthComparator comparator,
                        int recordWidth) {
    this.data = data;
    this.index = index;
    this.extractor = extractor;
    this.comparator = comparator;
    this.recordWidth = recordWidth;
  }

  public void quickSort(final int from, final int to) {
    final int len = to - from;
    // Selection sort on smallest arrays
    if (len < QUICKSORT_NO_REC) {
      selectionSort(from, to);
      return;
    }
    // Choose a partition element, v
    int m = from + len / 2;
    int l = from;
    int n = to - 1;
    if (len > QUICKSORT_MEDIAN_OF_9) { // Big arrays, pseudomedian of 9
      int s = len / 8;
      l = med3(l, l + s, l + 2 * s);
      m = med3(m - s, m, m + s);
      n = med3(n - 2 * s, n - s, n);
    }
    m = med3(l, m, n); // Mid-size, med of 3
//    final int v = index[m];
    // Establish Invariant: v* (<v)* (>v)* v*
    int a = from, b = a, c = to - 1, d = c;
    while (true) {
      int comparison;
      while (b <= c && (comparison = compareIndices(b, m)) <= 0) {
        if (comparison == 0)
          swap(index, a++, b);
        b++;
      }
      while (c >= b && (comparison = compareIndices(c, m)) >= 0) {
        if (comparison == 0)
          swap(index, c, d--);
        c--;
      }
      if (b > c)
        break;
      swap(index, b++, c--);
    }
    // Swap partition elements back to middle
    int s = Math.min(a - from, b - a);
    swap(index, from, b - s, s);
    s = Math.min(d - c, to - d - 1);
    swap(index, b, to - s, s);

    System.out.println("Partitioning done ...");
    displayData(s);

    // Recursively sort non-partition-elements
    if ((s = b - a) > 1)
      quickSort(from, from + s);
    if ((s = d - c) > 1)
      quickSort(to - s, to);
  }

  private void displayData(int s) {
    System.out.println("Partition at: " + s);
    for (int idx : index) {
      T t = extractor.objectAtIndex(data, idx, recordWidth);
      System.out.print(t + "  ");
    }
    System.out.println();
  }

  private void selectionSort(final int from, final int to) {
    for (int i = from; i < to - 1; i++) {
      int m = i;
      for (int j = i + 1; j < to; j++)
        if (compareIndices(j, m) < 0)
          m = j;
      if (m != i) {
        final int u = index[i];
        index[i] = index[m];
        index[m] = u;
      }
    }
  }

  private int compareIndices(int a, int b) {
    int objIndex = index[a];
    int objIndex2 = index[b];

    return comparator.compareFixedWidthObjects(data, objIndex, objIndex2, recordWidth);
  }

  private int med3(final int a, final int b, final int c) {
//    final int ab = (Integer.compare((x[a]), (x[b])));
    final int ab = compareIndices(a, b);

//    final int ac = (Integer.compare((x[a]), (x[c])));
    final int ac = compareIndices(a, c);

//    final int bc = (Integer.compare((x[b]), (x[c])));
    final int bc = compareIndices(b, c);

    return (ab < 0 ? (bc < 0 ? b : ac < 0 ? c : a) : (bc > 0 ? b : ac > 0 ? c : a));
  }

  private static void swap(final int x[], final int a, final int b) {
    final int t = x[a];
    x[a] = x[b];
    x[b] = t;
  }

  /**
   * Swaps two sequences of elements of an array.
   *
   * @param x an array.
   * @param a a position in {@code x}.
   * @param b another position in {@code x}.
   * @param n the number of elements to exchange starting at {@code a} and
   *          {@code b}.
   */
  private static void swap(final int[] x, int a, int b, final int n) {
    for (int i = 0; i < n; i++, a++, b++)
      swap(x, a, b);
  }

}
