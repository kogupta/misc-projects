package interview.epi.searching;

import static interview.epi.array.SortFunctions.assertionStatus;

// start at TOP-RIGHT
public final class SearchNonDecreasingMatrix {
  public static void main(String[] args) {
    assertionStatus();

    int[][] xss = {
        {-1, 2, 4, 4, 6},
        {1, 5, 5, 9, 21},
        {3, 6, 6, 9, 22},
        {3, 6, 8, 10, 24},
        {6, 8, 9, 12, 25},
        {8, 10, 12, 13, 40}
    };

    assert !contains(xss, 7);
    assert contains(xss, 8);
    assert !contains(xss, 42);
  }

  private static boolean contains(int[][] xss, final int k) {
    final int rows = xss.length, cols = xss[0].length;

    int row = 0, col = cols - 1;

    while (row < rows && col > 0) {
      int p = xss[row][col];
      if (p == k) {
        System.out.printf("Found! at: row:%d, column: %d%n", row, col);
        return true;
      }

      System.out.printf("(%d, %d) -> ", row, col);
      if (p < k) {
        // k greater than last element of a row
        // so, k > all row elements
        // search next row, same column
        row++;
      } else {
        // p > k
        // k less than element
        // so, k < next elements in this column
        // search previous column, same row
        col--;
      }
    }

    System.out.println("-X-");
    return false;
  }
}
