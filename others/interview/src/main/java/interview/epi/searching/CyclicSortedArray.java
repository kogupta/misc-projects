package interview.epi.searching;

import static interview.epi.array.SortFunctions.assertionStatus;

// An array is said to be cyclically sorted if it is possible to
// cyclically shift its entries so that it becomes sorted.
// for the example array: cyclic left shift by 4 leads to a sorted array
//
// Elements are DISTINCT!!
// if duplicates, then linear search
public final class CyclicSortedArray {
  public static void main(String[] args) {
    assertionStatus();

    // smallest
    int[] xs = {378, 478, 550, 631, 103, 203, 220, 234, 279, 368};
    //                              ^^^
    assert indexOfSmallestNumber(xs) == 4;
    assert indexOfLargestNumber(xs) == 3;
    assert indexOfLargestNumber2(xs) == 3;
  }

  private static int indexOfSmallestNumber(int[] xs) {
    // compare an element with the last element
    // ... bigger ... <smallest> ... lesser ... last
    int lo = 0, hi = xs.length - 1, last = xs[xs.length - 1];

    while (lo < hi) {
      int mid = lo + (hi - lo) / 2;
      assert xs[mid] != last : "Duplicates! index " + mid + " and last!";
      if (xs[mid] > last) {
        // search right
        lo = mid + 1;
      } else {
        // mid < last
        // search left
        hi = mid;
      }
    }

    assert lo == hi: String.format("Expected equals; got (lo, hi) => (%d, %d)", lo, hi);

    return lo;
  }

  private static int indexOfLargestNumber(int[] xs) {
    int n = indexOfSmallestNumber(xs);
    return n == 0 ? xs.length - 1 : n - 1;
  }

  // error prone
  private static int indexOfLargestNumber2(int[] xs) {
    // compare an element with the last element
    // ... bigger ... <largest> ... lesser ... last
    int lo = 0, hi = xs.length - 1, last = xs[xs.length - 1];

    while (hi -lo > 1) {
      int mid = lo + (hi - lo) / 2;
      assert xs[mid] != last : "Duplicates! index " + mid + " and last!";
      if (xs[mid] > last) {
        // search right
        lo = mid;
      } else {
        // mid < last
        // search left
        hi = mid - 1;
      }
    }

    assert hi == lo + 1 : String.format("got (lo, hi) => (%d, %d)", lo, hi);

    return xs[lo] > xs[hi] ? lo : hi;
  }
}
