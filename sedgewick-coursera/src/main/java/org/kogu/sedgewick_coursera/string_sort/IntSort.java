package org.kogu.sedgewick_coursera.string_sort;

import java.util.Arrays;

import static org.kogu.sedgewick_coursera.CommonFunctions.createShuffledArray;
import static org.kogu.sedgewick_coursera.CommonFunctions.time;
import static org.kogu.sedgewick_coursera.sort.SortFunctions.isSorted;

// Surprise: Sedgewick specialized Int sort faster than JDK quicksort!
// specialized IntSort -vs- JDK quicksort
//  sample size: 10             | 5.120 μs -vs- 3.982 μs
//  sample size: 100            | 3.982 μs -vs- 8.533 μs
//  sample size: 1,000          | 22.76 μs -vs- 138.8 μs
//  sample size: 10,000         | 195.7 μs -vs- 1.143 ms
//  sample size: 100,000        | 2.088 ms -vs- 12.08 ms
//  sample size: 1,000,000      | 29.50 ms -vs- 119.0 ms
//  sample size: 10,000,000     | 309.8 ms -vs- 842.6 ms
//  sample size: 100,000,000    | 2.676 s  -vs- 9.134 s
public final class IntSort {
  private static final int BITS_PER_BYTE = Byte.SIZE;
  private static final int w = Integer.BYTES;  // each int is 4 bytes

  private IntSort() {}

  public static void sort(int[] a) {
    final int R = 1 << BITS_PER_BYTE;    // each bytes is between 0 and 255
    final int MASK = R - 1;              // 0xFF

    int n = a.length;
    int[] aux = new int[n];

    for (int d = 0; d < w; d++) {

      // compute frequency counts
      int[] count = new int[R + 1];
      for (int item : a) {
        int c = (item >> BITS_PER_BYTE * d) & MASK;
        count[c + 1]++;
      }

      // compute cumulates
      for (int r = 0; r < R; r++)
        count[r + 1] += count[r];

      // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
      if (d == w - 1) {
        int shift1 = count[R] - count[R / 2];
        int shift2 = count[R / 2];
        for (int r = 0; r < R / 2; r++)
          count[r] += shift1;
        for (int r = R / 2; r < R; r++)
          count[r] -= shift2;
      }

      // move data
      for (int value : a) {
        int c = (value >> BITS_PER_BYTE * d) & MASK;
        aux[count[c]++] = value;
      }

      // copy back
      System.arraycopy(aux, 0, a, 0, n);
    }
  }

  public static void main(String[] args) {
    warmup();

    for (int i = 10; i <= 100_000_000; i *= 10) {
      System.out.printf("sample size: %,d%n", i);
      int[] xs = createShuffledArray(i);
      int[] copy = Arrays.copyOf(xs, xs.length);
      time(() -> sort(xs));
      time(() -> Arrays.sort(copy));
    }
  }

  private static void warmup() {
    for (int warmup = 1; warmup <= 100; warmup++) {
      if (warmup % 10 == 0)
        System.out.println("warmup: " + warmup);

      for (int i = 10; i <= 100_000; i *= 10) {
        int[] xs = createShuffledArray(i);
        sort(xs);
        assert isSorted(xs);

        int[] copy = Arrays.copyOf(xs, xs.length);
        Arrays.sort(copy);
      }
    }
  }


}
