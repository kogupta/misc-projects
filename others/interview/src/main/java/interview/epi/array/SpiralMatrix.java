package interview.epi.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SpiralMatrix {
  public static void main(String[] args) {
    _assertionStatus();

    int[][] grid = {
        {1, 2, 3, 4},
        {12, 13, 14, 5},
        {11, 16, 15, 6},
        {10, 9, 8, 7}
    };

    test(grid);


    int[][] grid2 = {
        {1, 2, 3, 4, 5},
        {16, 17, 18, 19, 6},
        {15, 24, 25, 20, 7},
        {14, 23, 22, 21, 8},
        {13, 12, 11, 10, 9}
    };

    test(grid2);
  }

  private static void test(int[][] grid) {
    List<Integer> path = path(grid);
    System.out.println(path);
    int[][] xs = buildGrid(path);
    for (int[] x : xs) {
      System.out.println(Arrays.toString(x));
    }

    System.out.println("===========================");
    for (int i = 0; i < xs.length; i++) {
      assert Arrays.equals(grid[i], xs[i]);
    }
  }

  private static List<Integer> path(int[][] grid) {
    int rows = grid.length;
    int cols = grid[0].length;
    assert rows == cols;

    int mid = isEven(rows) ? rows / 2 : (rows / 2 + 1);

    List<Integer> path = new ArrayList<>(rows * rows);

    for (int offset = 0; offset < mid; offset++) {
      int end = rows - offset - 1;

      // in middle of odd length row
      if (offset == end) {
        path.add(grid[offset][offset]);
      }

      // go right
      // row = offset, col = [offset, n - offset)
      for (int i = offset; i < end; i++) path.add(grid[offset][i]);

      // go down
      // row = [offset, n - offset), col = n - offset
      for (int i = offset; i < end; i++) path.add(grid[i][end]);

      // go left
      // row = end, col = [n - offset, offset)
      // opposite direction
      for (int i = end; i > offset; i--) path.add(grid[end][i]);

      // go top
      // row = [n - offset, offset), col = offset
      // opposite direction
      for (int i = end; i > offset; i--) path.add(grid[i][offset]);
    }

    return path;
  }

  private static boolean isEven(int rows) {
    return (rows / 2) * 2 == rows;
  }

  private static int[][] buildGrid(List<Integer> spiralPath) {
    int n = (int) Math.sqrt(spiralPath.size());
    assert n * n == spiralPath.size();

    int[][] result = new int[n][n];

    int mid = isEven(n) ? n / 2 : (n / 2 + 1);

    int idx = 0;
    for (int offset = 0; offset < mid; offset++) {
      int end = n - offset - 1;
      if (offset == end) {
        // in middle for odd n
        result[offset][offset] = spiralPath.get(spiralPath.size() - 1);
      }

      // right
      for (int i = offset; i < end; i++) result[offset][i] = spiralPath.get(idx++);

      // go down
      // row = [offset, n - offset), col = n - offset
      for (int i = offset; i < end; i++) {
        result[i][end] = spiralPath.get(idx++);
      }

      // go left
      // row = end, col = [n - offset, offset)
      // opposite direction
      for (int i = end; i > offset; i--) {
        result[end][i] = spiralPath.get(idx++);
      }

      // go top
      // row = [n - offset, offset), col = offset
      // opposite direction
      for (int i = end; i > offset; i--) {
        result[i][offset] = spiralPath.get(idx++);
      }
    }


    return result;
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = SpiralMatrix.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
  //</editor-fold>
}
