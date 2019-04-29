package interview.epi.searching;

import static interview.epi.array.SortFunctions.assertionStatus;

// given sorted array, elements maybe repeated
// find first index of k, -1 if not found
public final class FirstKIndex {
  public static void main(String[] args) {
    assertionStatus();
    int[] xs = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
    // k = 108, expected = 3
    // k = 285, expected = 6
    // k = -1,  expected = -1

    assert firstIndexOf(108, xs) == 3;
    assert firstIndexOf(285, xs) == 6;
    assert firstIndexOf(1, xs) == -1;
  }

  private static int firstIndexOf(int k, int[] sortedArr) {
    if (sortedArr == null || sortedArr.length == 0) return -1;
    int lo = 0, hi = sortedArr.length - 1, result = -1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      int p = sortedArr[mid];
      if (k <= p) {
        hi = mid - 1;
        if (k == p) result = mid;
      } else {
        // k > p
        lo = mid + 1;
      }
    }

    return result;
  }
}
