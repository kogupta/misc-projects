package interview.leet_medium;

import java.util.Arrays;

/**
 * Given map of 1s and 0s - find # of islands.
 *
 * Current solution: union-find with path compression
 * Other solution: undirected graph, find connected components
 *
 */
public final class IslandCount {
  private final boolean[] land;

  private final int rows, columns;
  private final WQUnion union;
  private final int[][] map;

  public IslandCount(int[][] map) {
    this.map = map;

    rows = map.length;
    columns = map[0].length;

    int n = rows * columns;
    union = new WQUnion(n);
    union.populateMap(map, rows, columns);

    land = landArray(map);
  }

  public void readMap() {
    for (int r = 0; r < rows; r++) {
      int[] row = map[r];
      for (int c = 0; c < columns; c++) {
        int element = row[c];
        if (element == 0) { continue; }

        assert element == 1;
        RCPair pair = RCPair.of(r, c, columns);

        maybeConnect(union, pair, pair.above());
        maybeConnect(union, pair, pair.below());
        maybeConnect(union, pair, pair.left());
        maybeConnect(union, pair, pair.right());
      }
    }
  }

  private static boolean[] landArray(int[][] map) {
    int rows = map.length;
    int columns = map[0].length;
    int n = rows * columns;
    boolean[] land = new boolean[n];
    for (int r = 0; r < map.length; r++) {
      int[] row = map[r];
      for (int c = 0; c < row.length; c++) {
        int element = row[c];
        land[r * columns + c] = element == 1;
      }
    }

    return land;
  }

  private void maybeConnect(WQUnion union, RCPair from, RCPair to) {
    if (isValidCoordinate(rows, columns, from)
        && isValidCoordinate(rows, columns, to)) {
      int p = from.toLinearIndex();
      int q = to.toLinearIndex();
      if (land[p] && land[q]) {
        union.connect(p, q);
//        System.out.printf("   connecting %s => %s, components: %d%n", from, to, union.components);
      }
    }
  }

  private static boolean isValidCoordinate(int rows, int cols, RCPair pair) {
    boolean validRow = pair.row >= 0 && pair.row < rows;
    boolean validColumn = pair.column >= 0 && pair.column < cols;
    return validRow && validColumn;
  }

  private static final class RCPair {
    final int row, column, columns;

    private RCPair(int row, int column, int numColumns) {
      this.row = row;
      this.column = column;
      this.columns = numColumns;
    }

    public int toLinearIndex() {
      return row * columns + column;
    }

    public RCPair above() {
      return new RCPair(row - 1, column, columns);
    }

    public RCPair below() {
      return new RCPair(row + 1, column, columns);
    }

    public RCPair left() {
      return new RCPair(row, column - 1, columns);
    }

    public RCPair right() {
      return new RCPair(row, column + 1, columns);
    }

    @Override
    public String toString() {
      return String.format("[%d, %d] -> %d", row, column, toLinearIndex());
    }

    public static RCPair of(int r, int c, int numColumns) {
      return new RCPair(r, c, numColumns);
    }
  }

  private static void assertionStatus() {
    String status = IslandCount.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }

  private static final class WQUnion {
    private final int[] ids;
    private final int[] sz;
    private int components;

    public void populateMap(int[][] map, int rows, int columns) {
      for (int r = 0; r < rows; r++) {
        int[] row = map[r];
        for (int c = 0; c < columns; c++) {
          int element = row[c];
          if (element == 0) { continue; }

          assert element == 1;
          int linearIndex = r * columns + c;
          ids[linearIndex] = linearIndex;
          sz[linearIndex] = 1;
          components++;
        }

        // debug
//        System.out.println();
//        System.out.println("expected: " + Arrays.toString(row));
//        int from = r * columns;
//        int to = from + columns;
//        System.out.println("obtained2:" + toString(ids, from, to));
//        System.out.println("size     :" + toString(sz, from, to));
//        System.out.println("obtained: " + Arrays.toString(ids));
      }

      display(columns);
    }

    public void display(int columns) {
      System.out.println("--- ids ---");
      rowDisplay(ids, columns);

      System.out.println("--- sizes ---");
      rowDisplay(sz, columns);
      System.out.println("components: " + components);
    }

    private static void rowDisplay(int[] arr, int columns) {
      for (int i = 0; i < arr.length; i++) {
        int id = arr[i];
        String draw = id < 0 ? "-" : String.valueOf(id);
        System.out.print(draw + "\t\t");
        if ((i + 1) % columns == 0) System.out.println();
      }
    }

    // NOTE: unlike, regular weighted quick union
    // initially fill all with 0s
    WQUnion(int N) {
      assert N > 0 : "require: universe of items > 0";

      ids = new int[N];
      Arrays.fill(ids, -1);

      sz = new int[N];
      Arrays.fill(sz, 0);

      components = 0;
    }

    public boolean isConnected(int p, int q) {
      return rootOf(p) == rootOf(q);
    }

    private int rootOf(int idx) {
      while (ids[idx] != idx) {
        int parent = ids[idx];
        int grandparent = ids[parent];
        ids[idx] = grandparent;

        idx = grandparent;
      }

      return idx;
    }

    public void connect(int p, int q) {
      // debug
//      System.out.println("-- is connected --");
//      System.out.println(Arrays.toString(ids));
//      System.out.println(Arrays.toString(sz));
//      System.out.println();

      int rootP = rootOf(p);
      int rootQ = rootOf(q);
      if (rootP == rootQ) return;

      if (sz[p] < sz[q]) {
        ids[rootP] = rootQ;
        sz[rootQ] += sz[rootP];
      } else {
        ids[rootQ] = rootP;
        sz[rootP] += sz[rootQ];
      }
      components--;
    }

    public int numComponents() {
      return components;
    }
  }

  public static void main(String[] args) {
    assertionStatus();

    test(createMap1(), 1);
    test(createMap2(), 3);
  }

  private static void test(int[][] map, int expected) {
    IslandCount islandCount = new IslandCount(map);
    islandCount.readMap();

    int numIslands = islandCount.union.numComponents();
    System.out.println("# of islands: " + numIslands);

    System.out.println("-- final state --");
    islandCount.union.display(map[0].length);
    System.out.println("=================================");

    assert numIslands == expected;
  }

  private static int[][] createMap1() {
    int[][] result = new int[4][5];
    int row = 0;
    result[row++] = new int[]{1, 1, 1, 1, 0};
    result[row++] = new int[]{1, 1, 0, 1, 0};
    result[row++] = new int[]{1, 1, 0, 0, 0};
    result[row++] = new int[]{0, 0, 0, 0, 0};

    return result;
  }

  private static int[][] createMap2() {
    int[][] result = new int[4][5];
    int row = 0;
    result[row++] = new int[]{1, 1, 0, 0, 0};
    result[row++] = new int[]{1, 1, 0, 0, 0};
    result[row++] = new int[]{0, 0, 1, 0, 0};
    result[row++] = new int[]{0, 0, 0, 1, 1};

    return result;
  }
}
