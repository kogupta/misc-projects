package interview.epi.sorting;

import java.util.Arrays;
import java.util.stream.IntStream;

import static interview.epi.array.SortFunctions.assertionStatus;

public final class SortedArrayIntersection {
  public static void main(String[] args) {
    assertionStatus();

    int[] xs = {2, 3, 3, 5, 5, 6, 7, 7, 8, 12};
    int[] ys = {5, 5, 6, 8, 8, 9, 10, 10};

    assert Arrays.equals(intersectSimple(xs, ys), new int[]{5, 6, 8});
    assert Arrays.equals(intersect(xs, ys), new int[]{5, 6, 8});
  }

  private static int[] intersect(int[] xs, int[] ys) {
    IntStream.Builder builder = IntStream.builder();

    int i = 0, j = 0;
    for (; i < xs.length && j < ys.length; ) {
      int x = xs[i], y = ys[j];
      if (x == y && (i == 0 || xs[i] != xs[i - 1])) {
        builder.add(x);
        i++;
        j++;
      } else if (x < y) i++;
      else /* x > y*/ j++;
    }

    int[] array = builder.build().toArray();
//    System.out.println(Arrays.toString(array));
    return array;
  }

  private static int[] intersectSimple(int[] xs, int[] ys) {
    int[] smaller = xs.length <= ys.length ? xs : ys;
    int[] larger = xs.length > ys.length ? xs : ys;
    IntStream.Builder builder = IntStream.builder();
    for (int i = 0; i < smaller.length; i++) {
      int x = smaller[i];
      if (i == 0 || smaller[i] != smaller[i - 1]) {
        int idx = Arrays.binarySearch(larger, x);
        if (idx >= 0) builder.add(x);
      }
    }

    int[] array = builder.build().toArray();
    return array;
  }
}
