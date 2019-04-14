package org.kogu.sedgewick_coursera.string_sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static java.lang.System.out;
import static org.kogu.sedgewick_coursera.CommonFunctions.shuffle;
import static org.kogu.sedgewick_coursera.CommonFunctions.time;
import static org.kogu.sedgewick_coursera.sort.SortFunctions.isSorted;

// given: an array of Keys, universe of keys is small/fixed/restricted
// examples: an array of characters [character in a..z]
//           an array of digits [digit 0..9]
// Sample timings:
// lsd vs jdk merge sort
//  sample size: 10         | 89.89 μs -vs- 761.7 μs
//  sample size: 100        | 541.0 μs -vs- 3.804 ms
//  sample size: 1,000      | 3.188 ms -vs- 11.86 ms
//  sample size: 10,000     | 13.55 ms -vs- 30.76 ms
//  sample size: 100,000    | 83.60 ms -vs- 415.1 ms
//  sample size: 1,000,000  | 2.258 s -vs- 2.960 s
//  sample size: 10,000,000 | 28.44 s -vs- 43.77 s
//
// Please note: key indexed sorting for fixed-width strings is fast/probably better than JDK merge sort
// but it is no patch on JDK quicksort!
public final class KeyIndexedCounting {
  private static final Random r = new Random(31_01_2010);
  private static final Comparator<String> cmp = Comparator.comparingLong(Long::valueOf);

  public static void main(String[] args) {
    _assertionStatus();

    final int width = 10;

    for (int i = 10; i <= 10_000_000; i *= 10) {
      out.printf("sample size: %,d%n", i);

      String[] numbers = generatePhoneNumbers(i, width);
//    out.println(join(",\n", numbers));
      time(() -> lsd(width, numbers));
      assert isSorted(numbers, cmp);
      System.gc();

      // comparision
      shuffle(r, numbers);
      time(() -> Arrays.sort(numbers, cmp));
      System.gc();

      shuffle(r, numbers);
      time(() -> Arrays.stream(numbers).mapToLong(s -> Long.valueOf(s)).sorted().toArray());
      System.gc();
    }

  }

  private static void lsd(int width, String[] numbers) {
    String[] aux = new String[numbers.length];
    int[] hist = new int[11]; // set of chars = 10 digits, set size + 1
    for (int idx = width - 1; idx >= 0; idx--) {
//      out.println("index: " + idx);

      // clear/reset histogram
      Arrays.fill(hist, 0);

      for (String number : numbers) {
        int n = extractInt(idx, number);
        hist[n + 1]++;
      }

      for (int i = 1; i < hist.length; i++)
        hist[i] += hist[i - 1];

      for (String number : numbers) {
        int n = extractInt(idx, number);
        int auxIdx = hist[n]++;
        aux[auxIdx] = number;
      }
      System.arraycopy(aux, 0, numbers, 0, aux.length);

//      out.println("Current state: " + join(",\n", numbers));
//      out.println("-------");
    }
  }

  private static int extractInt(int idx, String number) {
    int n = number.charAt(idx) - '0';
    assert 0 <= n && n <= 9;
    return n;
  }

  private static String[] generatePhoneNumbers(int n, int width) {
    String[] phoneNumbers = new String[n];
    StringBuilder sb = new StringBuilder(width);
    for (int i = 0; i < n; i++) {
      phoneNumbers[i] = generate(width, sb);
      sb.setLength(0);
    }

    return phoneNumbers;
  }

  private static void sort(int n, int[] xs) {
    //    out.print("tracking state: ");
    int[] histogram = new int[n + 1];
    histogram[0] = 0;
    for (int p : xs)
      histogram[p + 1]++;

    out.println(Arrays.toString(histogram));

    // explicit start indices
//    out.print("start indices: ");
    for (int i = 1; i < histogram.length; i++) {
      histogram[i] = histogram[i] + histogram[i - 1];
    }
//    out.println(Arrays.toString(histogram));

    // layout values
    for (int i = 0; i < n; i++) {
      int start = histogram[i];
      int end = histogram[i + 1];
      for (int j = start; j < end; j++)
        xs[j] = i;
    }

//    out.println(Arrays.toString(xs));
  }

  private static void _assertionStatus() {
    String status = KeyIndexedCounting.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }

  private static String generate(int width, StringBuilder sb) {
    for (int i = 0; i < width; i++) {
      int n = r.nextInt(10);
      sb.append(n);
    }
    return sb.toString();
  }
}
