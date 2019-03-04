package org.kogu.sedgewick_coursera.sort;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public enum SortFunctions {
  ;

  public static <T extends Comparable<T>> void assertSorted(T[] xs) {
    for (int i = 0; i < xs.length - 1; i++) {
      T x = xs[i];
      T y = xs[i + 1];
      int n = x.compareTo(y);
      if (n > 0) throw new AssertionError();
    }
  }

  public static void assertSorted(int[] xs) {
    for (int i = 0; i < xs.length - 1; i++) {
      int x = xs[i];
      int y = xs[i + 1];
      if (x > y)
        throw new AssertionError();
    }
  }

  public static <T extends Comparable<T>> boolean isSorted(T[] xs) {
    for (int i = 1; i < xs.length; i++) {
      T t = xs[i];
      T prev = xs[i - 1];
      if (less(t, prev)) return false;
    }

    return true;
  }

  public static boolean isSorted(int[] xs) {
    for (int i = 1; i < xs.length; i++) {
      int curr = xs[i];
      int prev = xs[i - 1];
      if (curr < prev) return false;
    }

    return true;
  }

  public static void assertSorted(int[] xs, int from, int to) {
    for (int i = from + 1; i <= to; i++) {
      int curr = xs[i];
      int prev = xs[i - 1];
      if (curr < prev) {
        System.err.println(Arrays.toString(xs));
        System.err.printf("xs[%d] = %d, xs[%d] = %d%n", i, curr, i - 1, prev);
        throw new AssertionError();
      }
    }
  }

  public static <T extends Comparable<T>> boolean less(T a, T b) {
    return a.compareTo(b) < 0;
  }

  public static <T extends Comparable<T>> boolean less(T[] arr, int i, int j) {
    return arr[i].compareTo(arr[j]) < 0;
  }

  public static <T extends Comparable<T>> void swap(T[] xs, int i, int j) {
    T t = xs[i];
    xs[i] = xs[j];
    xs[j] = t;
  }

  public static boolean less(int[] arr, int i, int j) {
    return arr[i] < arr[j];
  }

  public static void swap(int[] xs, int i, int j) {
    int t = xs[i];
    xs[i] = xs[j];
    xs[j] = t;
  }

  public static void time(Runnable r) {
    long t0 = System.nanoTime();
    r.run();
    long nanos = System.nanoTime() - t0;
    TimeUnit unit = chooseUnit(nanos);
    double value = (double) nanos / NANOSECONDS.convert(1, unit);

    System.out.printf("%.4g %s%n", value, abbreviate(unit));
  }

  private static TimeUnit chooseUnit(long nanos) {
    if (DAYS.convert(nanos, NANOSECONDS) > 0) {
      return DAYS;
    }
    if (HOURS.convert(nanos, NANOSECONDS) > 0) {
      return HOURS;
    }
    if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
      return MINUTES;
    }
    if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
      return SECONDS;
    }
    if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
      return MILLISECONDS;
    }
    if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
      return MICROSECONDS;
    }
    return NANOSECONDS;
  }

  private static String abbreviate(TimeUnit unit) {
    switch (unit) {
      case NANOSECONDS:
        return "ns";
      case MICROSECONDS:
        return "\u03bcs"; // μs
      case MILLISECONDS:
        return "ms";
      case SECONDS:
        return "s";
      case MINUTES:
        return "min";
      case HOURS:
        return "h";
      case DAYS:
        return "d";
      default:
        throw new AssertionError();
    }
  }

  public static int[] createShuffledArray(int size) {
    int[] xs = createArray(size);
    StdRandom.shuffle(xs);
    return xs;
  }

  public static int[] createArray(int size) {
    int[] xs = new int[size];
    Arrays.setAll(xs, i -> i);
    return xs;
  }

  //<editor-fold desc="testing methods">
  public static void main(String[] args) {
    // empty/singleton set is always sorted
    assertSorted(new int[]{});
    assertSorted(new int[]{1});

    // sorted array
    int[] xs = new int[10];
    Arrays.setAll(xs, i -> i);
    assertSorted(xs);

    // all elements equal
    Arrays.fill(xs, 1);
    assertSorted(xs);

    Arrays.setAll(xs, i -> i);
    StdRandom.shuffle(xs);
    expectAssertionError(xs);

    int len = 'z' - 'a' + 1;
    String[] chars = new String[len];
    char c = 'a';
    for (int i = 0; i < len; i++, c++) chars[i] = String.valueOf(c);
    System.out.println(Arrays.toString(chars));
    assertSorted(chars);
    StdRandom.shuffle(chars);
    expectAssertionError(chars);
  }

  private static void expectAssertionError(int[] xs) {
    try {
      assertSorted(xs);
      throw new IllegalStateException("-- sort assertion gone wrong! --");
    } catch (AssertionError ignore) {}
  }

  private static <T extends Comparable<T>> void expectAssertionError(T[] xs) {
    try {
      assertSorted(xs);
      throw new IllegalStateException("-- sort assertion gone wrong! --");
    } catch (AssertionError ignore) {}
  }

  //</editor-fold>
}
