package interview;

import java.util.Arrays;
import java.util.Random;

// does not work: array index fiddling!
// reference: https://www.youtube.com/watch?v=MHNTl_NvOj0
public final class Median2SortedArrays {
  private static final Random rnd = new Random(31_01_2010);

  public static void main(String[] args) {
    int[] inputSizes = {10, 14, 16, 20};
    for (int n : inputSizes) {
      Triple triple = generate(n);
      double median = findMedian(triple.xs, 0, triple.xs.length - 1,
          triple.ys, 0, triple.ys.length - 1);
      assert triple.expectedMedian == median :
          String.format("Expected: %.2f, got: %.2f", triple.expectedMedian, median);

      System.out.println("-----------------------");
    }
  }

  private static double findMedian(int[] as, int start, int end,
                                   int[] bs, int start2, int end2) {
    System.out.printf("%s, %d - %d%n", Arrays.toString(as), start, end);
    System.out.printf("%s, %d - %d%n", Arrays.toString(bs), start2, end2);

    if (((end - start) == 1) && ((end2 - start2) == 1)) {
      int a = Math.max(as[start], bs[start2]);
      int b = Math.min(as[end], bs[end2]);
      return ((double) (a + b)) / 2;
    }

    int medianIdx = (start + end) / 2;
    int medianIdx2 = (start2 + end2) / 2;
    System.out.println("median indices: " + medianIdx + ", " + medianIdx2);
    System.out.println();

    int m1 = as[medianIdx];
    int m2 = bs[medianIdx2];

    // medians equal?
    if (m1 == m2) return m1;

    // trim left/right
    // as ... < m1 <= median <= m2 < ...bs
    if (m1 < m2) {
      // as ... < m1 <= median <= m2 < ...bs
      start = medianIdx;
      end2 = medianIdx2;
    } else {
      // bs ... < m2 <= median <= m1 < ...as
      end = medianIdx;
      start2 = medianIdx2;
    }

    return findMedian(as, start, end, bs, start2, end2);
  }

  private static Triple generate(int n) {
    int[] xs = new int[n];
    Arrays.setAll(xs, i -> rnd.nextInt(10));
    Arrays.sort(xs);
    double median = n % 2 == 0 ? ((double) xs[n / 2] + xs[n / 2 - 1]) / 2 : xs[n / 2];
    int[] left = new int[n / 2], right = new int[n - n / 2];
    for (int i = 0, len = xs.length; i < len; i++) {
      int[] target = i % 2 == 0 ? right : left;
      target[i / 2] = xs[i];
    }

    System.out.println(Arrays.toString(xs));
    System.out.println(Arrays.toString(left));
    System.out.println(Arrays.toString(right));
    System.out.printf("%.2f%n", median);

    return new Triple(left, right, median);
  }

  private static final class Triple {
    final int[] xs, ys;
    final double expectedMedian;

    private Triple(int[] xs, int[] ys, double median) {
      this.xs = xs;
      this.ys = ys;
      this.expectedMedian = median;
    }
  }

}
