package interview;

import java.util.Arrays;
import java.util.Random;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public final class Find2MissingNumbers {
  private static final Random rnd = new Random(31_01_2010);

  public static void main(String[] args) {
    for (int i = 4; i <= 10_000; i *= 4) {
      findMissing(shuffledNumbers(i));
      System.out.println();
    }
  }

  private static void findMissing(Triple triple) {
    int[] input = triple.input;
    int sum = Arrays.stream(input).sum();
    int end = input.length + 2;
    int expectedSum = end * (end + 1) / 2;

    int diff = expectedSum - sum;
    int mid = diff / 2;
    // diff is sum of two ints -> it cannot be same ints
    // missing numbers in between [1, mid] and (mid, end]

    // xor trick [1, mid] -> remaining number is one missing
    // xor trick (mid, end] -> other missing number

    IntBinaryOperator xorOp = (a, b) -> a ^ b;
    int leftXor = IntStream.rangeClosed(1, mid).reduce(0, xorOp);
    int rightXor = IntStream.rangeClosed(mid + 1, end).reduce(0, xorOp);

    for (int i : input) {
      if (i <= mid) leftXor ^= i;
      else rightXor ^= i;
    }

    System.out.println("Found " + leftXor + " and " + rightXor);

    assert triple.missing == leftXor;
    assert triple.missing2 == rightXor;
  }

  private static Triple shuffledNumbers(int n) {
    int[] xs = new int[n];
    Arrays.setAll(xs, i -> i + 1);
    shuffle(xs);
    int x = xs[n - 2], y = xs[n - 1];
    int[] result = new int[n - 2];
    System.arraycopy(xs, 0, result, 0, n - 2);
    System.out.println("numbers [at most 20]: " + Arrays.stream(result).limit(20).mapToObj(String::valueOf).collect(joining(",")));
    System.out.println("missing: " + x + ", " + y);

    return new Triple(result, x, y);
  }

  private static void shuffle(int[] arr) {
    for (int i = arr.length; i > 1; i--)
      swap(arr, i - 1, rnd.nextInt(i));
  }

  private static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  private static final class Triple {
    final int[] input;
    final int missing, missing2;

    private Triple(int[] input, int missing, int missing2) {
      this.input = input;
      this.missing = Math.min(missing, missing2);
      this.missing2 = Math.max(missing, missing2);
    }

  }
}
