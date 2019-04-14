package interview;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class ZeroSumSubArray {
  public static void main(String[] args) {
    int[] xs = {-2, 4, 9, 3, -8, -6, 4, 9, 1, 9};
    System.out.println(Arrays.toString(xs));

    // this is only for tracing purpose
    Map<Integer, Integer> prefixSum = new LinkedHashMap<>(xs.length);
    Multimap<Integer, Integer> inv = LinkedListMultimap.create();

    prefixSum.put(0, 0);
    inv.put(0, 0);
    for (int i = 1, len = xs.length; i <= len; i++) {
      int cumulativeSum = prefixSum.get(i - 1) + xs[i - 1];

      prefixSum.put(i, cumulativeSum);

      inv.put(cumulativeSum, i);
    }

    displayMap(prefixSum);

    System.out.println(inv);

    System.out.println("Zero sum arrays between: ");
    String s = inv.asMap().values().stream()
        .filter(integers -> integers.size() > 1)
        .map(ZeroSumSubArray::extractMinMax)
        .map(p -> p.from + ".." + p.to)
        .collect(Collectors.joining(", "));
    System.out.println(s);
  }

  private static Pair extractMinMax(Collection<Integer> xs) {
    assert xs != null && xs.size() > 1;

    Integer[] array = xs.toArray(new Integer[0]);
    return new Pair(array[0], array[array.length - 1]);
  }

  private static final class Pair {
    final int from, to;

    private Pair(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

  private static void displayMap(Map<Integer, Integer> prefixSum) {
    String s = prefixSum.entrySet()
        .stream()
        .map(e -> e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining(" | "));
    System.out.println(s);
  }
}
