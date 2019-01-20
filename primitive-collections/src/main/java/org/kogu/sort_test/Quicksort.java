package org.kogu.sort_test;

import it.unimi.dsi.fastutil.ints.IntArrays;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

import static org.kogu.sort_test.BBFixedWidthComparator.intComparator;

public final class Quicksort<T extends Comparable<T>> {
  private static final int QUICKSORT_NO_REC = 16;
  private static final int QUICKSORT_MEDIAN_OF_9 = 128;

  private final ByteBuffer data;
  private final BBFixedWidthComparator comparator;
  private final int recordWidth;
  private final int[] index;

  public Quicksort(ByteBuffer data,
                   BBFixedWidthComparator comparator,
                   int recordWidth) {
    this.data = data;
    this.comparator = comparator;
    this.recordWidth = recordWidth;
    int numRecords = (data.limit() - data.position()) / recordWidth;
    index = new int[numRecords];
    Arrays.setAll(index, i -> i);
  }

  public int[] sort() {
    sort(0, index.length - 1);
    return index;
  }

  private void sort(int lo, int hi) {
    if (hi <= lo) return;

    final int len = hi - lo;
    // Selection sort on smallest arrays
    if (len < QUICKSORT_NO_REC) {
      selectionSort(lo, hi + 1);
      return;
    }

    int j = partition(lo, hi);
    sort(lo, j - 1);
    sort(j + 1, hi);
  }

  private int partition(int lo, int hi) {
    // Partition into a[lo..i-1], a[i], a[i+1..hi].
    int i = lo, j = hi + 1; // left and right scan indices
//    Comparable v = a[lo]; // partitioning item
    while (true) {
      // Scan right, scan left, check for scan complete, and exchange
      while (less(++i, lo)) if (i == hi) break;
      while (less(lo, --j)) if (j == lo) break;
      if (i >= j) break;
      swap(index, i, j);
    }

    swap(index, lo, j); // Put v = a[j] into position
    return j; // with a[lo..j-1] <= a[j] <= a[j+1..hi].
  }

  private boolean less(int a, int b) {
    int c = compareIndices(a, b);
    return c < 0;
  }

  private void selectionSort(final int from, final int to) {
    for (int i = from; i < to - 1; i++) {
      int m = i;
      for (int j = i + 1; j < to; j++)
        if (compareIndices(j, m) < 0)
          m = j;
      if (m != i) {
        swap(index, i, m);
      }
    }
  }

  private static void swap(int[] index, int a, int b) {
    final int u = index[a];
    index[a] = index[b];
    index[b] = u;
  }

  private int compareIndices(int a, int b) {
    int objIndex = index[a];
    int objIndex2 = index[b];

    return comparator.compareFixedWidthObjects(data, objIndex, objIndex2, recordWidth);
  }

  public static void main(String[] args) {
    int recordCount = 10_000;
    int recordWidth = Integer.BYTES;

    ByteBuffer buffer = ByteBuffer.allocate(recordWidth * recordCount);
    int[] ns = new int[recordCount];
    Arrays.setAll(ns, i -> i);
    IntArrays.shuffle(ns, new Random());
    for (int n : ns) buffer.putInt(n);
    buffer.flip();

    Consumer<ByteBuffer> c = bb -> System.out.print(bb.getInt() + " ");
    Functions.iterate(buffer.duplicate(), recordWidth, c);
    System.out.println();

    Quicksort<Integer> qsort = new Quicksort<>(buffer, intComparator, recordWidth);
    int[] index = qsort.sort();
    int[] obtained = new int[index.length];
    for (int idx = 0; idx < index.length; idx++) {
      int n = index[idx];
      obtained[idx] = buffer.getInt(n * recordWidth);
    }

    IntArrays.quickSort(ns);  // expected
    if (!Arrays.equals(ns, obtained)) {
      System.err.println("Sorting failed!");
      System.err.println("Expected: " + Arrays.toString(ns));
      System.err.println("Obtained: " + Arrays.toString(obtained));

      throw new AssertionError("Sorting failed!");
    }

    System.out.println("Sorting works: obtained => " + Arrays.toString(obtained));
  }
}
