package interview.epi.searching;

import static interview.epi.array.SortFunctions.assertionStatus;

public final class MinMax {
  public static void main(String[] args) {
    assertionStatus();

    int[] xs = {3, 2, 5, 1, 2, 4};

    Pair pair = minMaxOf(xs);
    assert pair.min == 1;
    assert pair.max == 5;
  }

  private static Pair minMaxOf(int[] xs) {
    assert xs != null && xs.length > 0;
    if (xs.length == 1) return Pair.of(xs[0], xs[0]);

    // xs is of at least length 2
    Pair p = Pair.of(xs[0], xs[1]);   // 1 comparision
    for (int i = 2; i < xs.length; i += 2) {
      p.compareAndUpdate(xs[i], xs[i + 1]); // 3 comparision
    }

    // last element if odd length array
    if (xs.length % 2 == 1) {
      int n = xs[xs.length - 1];
      // 2 comparision
      p.min = Math.min(p.min, n);
      p.max = Math.max(p.max, n);
    }

    return p;
  }

  private static final class Pair {
    int min, max;

    private Pair(int min, int max) {
      this.min = min;
      this.max = max;
    }

    void compareAndUpdate(int a, int b) {
      if (a > b) {
        min = Math.min(min, b);
        max = Math.max(max, a);
      } else {
        // a <= b
        min = Math.min(min, a);
        max = Math.max(max, b);
      }
    }

    public static Pair of(int min, int max) {
      return min > max ? new Pair(max, min) : new Pair(min, max);
    }
  }
}
