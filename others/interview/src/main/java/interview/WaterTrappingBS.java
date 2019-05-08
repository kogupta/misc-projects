package interview;

import static interview.epi.array.SortFunctions.assertionStatus;
import static java.lang.Math.min;

// given: a histogram as array of ints
// find: max amount of water to be trapped
// key idea:
// start seeking bucket endpoints from both sides [left and right]
// progress only if current height is LESS than other side
// track max also
public final class WaterTrappingBS {
  public static void main(String[] args) {
    assertionStatus();

    int[] xs = {1, 2, 1, 3, 4, 4, 5, 6, 2, 1, 3, 1, 3, 2, 1, 2, 4, 1};
    // index: [4, 16] acts as biggest "bucket"

    System.out.println(maxTrapped(xs));
  }

  private static int maxTrapped(int[] xs) {
    int i = 0, j = xs.length - 1, max = 0;
    Triplet trace = null;
    while (i < j) {
      int left = xs[i], right = xs[j];
      int height = min(left, right);
      int width = j - i;

      if (max < height * width) {
        max = height * width;
        trace = Triplet.of(height, i, j);
      }

      if (left == right) {
        i++;
        j--;
      } else if (left < right) i++;
      else j--;
    }

    assert trace != null;
    System.out.println("result: " + trace);

    return max;
  }

  private static final class Triplet {
    final int height, left, right;

    private Triplet(int height, int left, int right) {
      this.height = height;
      this.left = left;
      this.right = right;
    }

    @Override
    public String toString() {
      return String.format("height: %d, left: %d, right: %d",
          height, left, right);
    }

    static Triplet of(int height, int left, int right) {
      return new Triplet(height, left, right);
    }
  }
}
