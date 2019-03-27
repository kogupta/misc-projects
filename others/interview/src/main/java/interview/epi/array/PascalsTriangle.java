package interview.epi.array;

import java.util.Arrays;

public final class PascalsTriangle {
  public static void main(String[] args) {
    assertionStatus();

    for (int[] row : rows(5)) {
      System.out.println(Arrays.toString(row));
    }

  }

  public static int[][] rows(int n) {
    assert n >= 0: "`n` should be non-negative";

    int[][] xss = new int[n][];

    xss[0] = new int[]{1};
    for (int row = 1; row < n; row++) {
      xss[row] = new int[row + 1];
      xss[row][0] = 1;
      xss[row][row] = 1;
      for (int i = 1; i < row; i++) {
        xss[row][i] = xss[row - 1][i - 1] + xss[row - 1][i];
      }
    }

    return xss;
  }

  //<editor-fold desc="assertion status">
  private static void assertionStatus() {
    String status = PascalsTriangle.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
  //</editor-fold>
}
