package org.kogu.sedgewick_coursera;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.*;

public final class CommonFunctions {
  private CommonFunctions() {}

  public static void assertionStatus() {
    String status = CommonFunctions.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println();
  }

  public static <T> void swap(T[] xs, int i, int j) {
    T t = xs[i];
    xs[i] = xs[j];
    xs[j] = t;
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

    out.printf("%.4g %s%n", value, abbreviate(unit));
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
    int[] xs = createSortedArray(size);
    StdRandom.shuffle(xs);
    return xs;
  }

  public static int[] createSortedArray(int size) {
    int[] xs = new int[size];
    Arrays.setAll(xs, i -> i);
    return xs;
  }

  public static <T> void reverse(T[] array) {
    for (int i = 0; i < array.length / 2; i++) {
      int other = array.length - 1 - i;
      swap(array, i, other);
    }
  }

  public static void reverse(int[] array) {
    for (int i = 0; i < array.length / 2; i++) {
      int other = array.length - 1 - i;
      swap(array, i, other);
    }
  }

  public static void main(String[] args) {
    int[] xs = createSortedArray(10);
    System.out.println("before: " + Arrays.toString(xs));
    reverse(xs);
    System.out.println("after: " + Arrays.toString(xs));
  }
}
