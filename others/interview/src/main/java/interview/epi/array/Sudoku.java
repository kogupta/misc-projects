package interview.epi.array;

import java.util.Arrays;

public final class Sudoku {
  private static final int[] numbers = new int[10];

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  private static boolean nonZeroDuplicatesInRow(int[][] grid, int row) {
    assert 0 <= row && row < grid.length;

    int[] xs = grid[row];
    for (int x : xs) {
      assert x < numbers.length: "Expected number between 1 and 9, got: " + x;
      numbers[x] += 1;
    }

    return hasDuplicates();
  }

  private static boolean nonZeroDuplicatesInColumn(int[][] grid, int column) {
    assert 0 <= column && column < grid.length;

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < grid.length; i++) {
      int x = grid[i][column];
      assert x < numbers.length: "Expected number between 1 and 9, got: " + x;
      numbers[x] += 1;
    }

    return hasDuplicates();
  }

  private static boolean nonZDups(int[][] grid, RCPair start, RCPair end) {
    for (int r = start.row; r < end.row; r++) {
      for (int c = start.column; c < end.column; c++) {
        int n = grid[r][c];
        numbers[n] += 1;
      }
    }

    return hasDuplicates();
  }

  private static boolean hasDuplicates() {
    try {
      for (int i = 1; i < numbers.length; i++) {
        int number = numbers[i];
        if (number > 1) {
          return true;
        }
      }
      return false;
    } finally {
      clear();
    }
  }

  private static void clear() {
    Arrays.fill(numbers, 0);
  }

  public static void main(String[] args) {
    assertionStatus();

    int[][] grid = new int[2][];
    grid[0] = new int[]{5, 3, 0, 0, 7, 0, 0, 0, 0};
    grid[1] = new int[]{6, 7, 2, 1, 9, 5, 3, 4, 8};

    assert !nonZeroDuplicatesInRow(grid, 0);
    assert !nonZeroDuplicatesInRow(grid, 1);
  }

  private static void assertionStatus() {
    String status = Sudoku.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }

  private static final class RCPair {
    final int row, column;

    private RCPair(int row, int column) {
      this.row = row;
      this.column = column;
    }

    public static RCPair of(int row, int column) {
      assert 0 <= row && row <= 9;
      assert 0 <= column && column <= 9;

      return new RCPair(row, column);
    }
  }
}
