package interview.epi.searching;

import static interview.epi.array.SortFunctions.assertionStatus;

// given a sorted array: find indices whose value == index
// create a logical array whose values are: value - index
// search for 0 in that logical array
public final class IndexSameAsValue {
  public static void main(String[] args) {
    assertionStatus();

    int[] xs = {-2, 0, 2, 3, 6, 7, 9};
    // expected answer: 2, 3
    int got = findIndex(xs);
    assert got == 2 || got == 3 :
        "Expected: 2 or 3, got: " + got;

  }

  private static int findIndex(int[] xs) {
    int lo = 0, hi = xs.length - 1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      int diff = xs[mid] - mid;
      if (diff == 0) return mid;
      if (diff < 0) {
        // value at i is less than i
        // predicate A[i] == i WILL NOT BE SATISFIED
        // for indices less than i
        // so search right
        lo = mid + 1;
      } else {
        // diff > 0; search left
        hi = mid - 1;
      }
    }

    return -1;
  }
}
