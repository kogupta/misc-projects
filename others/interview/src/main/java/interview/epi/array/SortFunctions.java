package interview.epi.array;

import java.util.Arrays;
import java.util.Random;

public enum SortFunctions {
  ;

  private static final Random rnd = new Random(31012010L);

  public static void assertSorted(int[] xs) {
    for (int i = 0; i < xs.length - 1; i++) {
      int x = xs[i];
      int y = xs[i + 1];
      if (x > y)
        throw new AssertionError();
    }
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

  public static void shuffle(int[] arr) {
    for (int i = arr.length; i > 1; i--)
      swap(arr, i - 1, rnd.nextInt(i));
  }

  public static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  public static <T> void swap(T[] arr, int i, int j) {
    T tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  public static void assertionStatus() {
    String status = SortFunctions.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }

  public static String toString(int[] arr, int from, int to) {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int i = from; i < to; i++) {
      sb.append(arr[i]);
      if (i < to - 1) { sb.append(", ");}
    }

    sb.append(']');
    return sb.toString();
  }
}
