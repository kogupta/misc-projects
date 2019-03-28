package interview.epi.array;

import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class RotateMatrix {
  public static void main(String[] args) {
    _assertionStatus();

    int[][] matrix = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };
    rotate(matrix);
    horizontalReflection(matrix);
    verticalReflection(matrix);

    int[][] matrix2 = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };
    rotate(matrix2);
    horizontalReflection(matrix2);
    verticalReflection(matrix2);

    int[][] matrix3 = {
        {0, 1, 2, 3, 4},
        {5, 6, 7, 8, 9},
        {10, 11, 12, 13, 14},
        {15, 16, 17, 18, 19},
        {20, 21, 22, 23, 24}
    };
    rotate(matrix3);
    horizontalReflection(matrix3);
    verticalReflection(matrix3);
  }

  private static void rotate(int[][] matrix) {
    System.out.println("--- Rotate 90 ---");
    display(matrix);
    //assert square
    int r = matrix.length, c = matrix[0].length;
    assert r == c;

    int layers = r / 2;
    int lastIndex = r - 1;

    for (int offset = 0; offset < layers; offset++) {
      // offset == each layer
      int end = lastIndex - offset;

      for (int i = offset; i < end; i++) {
        int top = matrix[offset][i];
        int right = matrix[i][end];
        int bot = matrix[end][lastIndex - i];
        int left = matrix[lastIndex - i][offset];

        matrix[offset][i] = left;
        matrix[i][end] = top;
        matrix[end][lastIndex - i] = right;
        matrix[lastIndex - i][offset] = bot;
      }
    }

    System.out.println();
    display(matrix);
    System.out.println("===========================================");
  }

  private static void horizontalReflection(int[][] matrix) {
    System.out.println("--- reflect on horizontal symmetry ---");
    display(matrix);

    int r = matrix.length;
    int midRow = r / 2;

    for (int row = 0; row < midRow; row++) {
      int to = r - 1 - row;
      swapRow(matrix, row, to);
    }

    System.out.println();
    display(matrix);
    System.out.println("===========================================");
  }

  private static void verticalReflection(int[][] matrix) {
    System.out.println("--- reflect on vertical symmetry ---");
    display(matrix);

    int r = matrix.length, c = matrix[0].length;
    assert r == c;
    int midCol = r / 2;

    for (int col = 0; col < midCol; col++) {
      int to = c - 1 - col;
      swapColumn(matrix, col, to);
    }

    System.out.println();
    display(matrix);
    System.out.println("===========================================");
  }

  private static void swapRow(int[][] matrix, int row1, int row2) {
    int[] t = matrix[row2];
    matrix[row2] = matrix[row1];
    matrix[row1] = t;
  }

  private static void swapColumn(int[][] matrix, int col1, int col2) {
    for (int[] row : matrix) {
      SortFunctions.swap(row, col1, col2);
    }
  }

  private static void display(int[][] matrix) {
    for (int[] row : matrix) {
      String s = Arrays.stream(row)
          .mapToObj(n -> Strings.padStart(String.valueOf(n), 3, ' '))
          .collect(Collectors.joining(", "));
      System.out.println(s);
    }
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = RotateMatrix.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
  //</editor-fold>

}
