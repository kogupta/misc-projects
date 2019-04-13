package org.kogu.string_sort;

import java.util.Arrays;
import java.util.Random;

import static org.kogu.sedgewick_coursera.CommonFunctions.time;

// specialized Long sort -vs- JDK quicksort
//  sample size: 10         | 11.95 μs -vs- 3.413 μs
//  sample size: 100        | 12.52 μs -vs- 8.533 μs
//  sample size: 1,000      | 46.65 μs -vs- 198.5 μs
//  sample size: 10,000     | 513.1 μs -vs- 1.034 ms
//  sample size: 100,000    | 5.322 ms -vs- 16.66 ms
//  sample size: 1,000,000  | 60.18 ms -vs- 120.9 ms
//  sample size: 10,000,000 | 564.3 ms -vs- 757.8 ms
public final class LongSort {
  private static final Random r = new Random(31_01_2010);
  private static final int BITS_PER_BYTE = Byte.SIZE;
  private static final int w = Long.BYTES;  // each int is 4 bytes

  private LongSort() {}

  public static void sort(long[] a) {
    final int R = 1 << BITS_PER_BYTE;    // each bytes is between 0 and 255
    final int MASK = R - 1;              // 0xFF

    int n = a.length;
    long[] aux = new long[n];

    for (int d = 0; d < w; d++) {

      // compute frequency counts
      int[] count = new int[R + 1];
      for (long item : a) {
        int c = (int) ((item >> BITS_PER_BYTE * d) & MASK);
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
      for (long value : a) {
        int c = (int) ((value >> BITS_PER_BYTE * d) & MASK);
        aux[count[c]++] = value;
      }

      // copy back
      System.arraycopy(aux, 0, a, 0, n);
    }
  }

  public static void main(String[] args) {
    warmup();

    for (int i = 10; i <= 10_000_000; i *= 10) {
      System.out.printf("sample size: %,d%n", i);
      long[] xs = createShuffledArray(i);
      long[] copy = Arrays.copyOf(xs, xs.length);
      time(() -> sort(xs));
      time(() -> Arrays.sort(copy));
    }
  }

  private static void warmup() {
    for (int warmup = 1; warmup <= 100; warmup++) {
      if (warmup % 10 == 0)
        System.out.println("Warmup: count= " + warmup);

      for (int i = 10; i <= 100_000; i *= 10) {
        long[] xs = createShuffledArray(i);
        sort(xs);
        assert isSorted(xs);
        long[] copy = Arrays.copyOf(xs, xs.length);
        Arrays.sort(copy);
        assert isSorted(copy);
      }
    }
  }

  private static long[] createShuffledArray(int size) {
    long[] xs = new long[size];
    Arrays.setAll(xs, i -> i);
    shuffle(xs);
    return xs;
  }

  private static void shuffle(long[] a) {
    int n = a.length;
    for (int i = 0; i < n; i++) {
      int next = i + r.nextInt(n - i);     // between i and n-1
      swap(a, i, next);
    }
  }

  private static void swap(long[] xs, int i, int j) {
    long t = xs[i];
    xs[i] = xs[j];
    xs[j] = t;
  }

  private static boolean isSorted(long[] xs) {
    for (int i = 1; i < xs.length; i++) {
      long curr = xs[i];
      long prev = xs[i - 1];
      if (curr < prev) return false;
    }

    return true;
  }

}
