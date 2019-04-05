package interview.leet_medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given map of 1s and 0s - find # of islands.
 *
 * Current solution: undirected graph, find connected components
 * Other solution: union-find with path compression
 *
 */
public final class IslandCount2 {
  private static CC readMap(int[][] _map) {
    Map map = new Map(_map);

    Graph graph = new Graph(map.rows * map.columns);
    for (int r = 0; r < map.rows; r++) {
      int[] row = _map[r];
      for (int c = 0; c < row.length; c++) {
        if (row[c] == 0) {continue;}

        RCPair one = RCPair.of(r, c, map.columns);
        maybeConnect(graph, one, one.above(), map);
        maybeConnect(graph, one, one.below(), map);
        maybeConnect(graph, one, one.left(), map);
        maybeConnect(graph, one, one.right(), map);
      }
    }

    return new CC(graph, map);
  }

  private static void maybeConnect(Graph graph, RCPair pair, RCPair other, Map map) {
    if (map.isValidCoordinate(other) && map.valueAt(other) == 1) {
      graph.addEdge(pair.toLinearIndex(), other.toLinearIndex());
    }
  }

  private static final class Map {
    final int[][] data;
    final int rows, columns;

    private Map(int[][] data) {
      this.data = data;
      rows = data.length;
      columns = data[0].length;
    }

    public int valueAt(RCPair pair) {
      return data[pair.row][pair.column];
    }

    public boolean isValidCoordinate(RCPair pair) {
      boolean validRow = 0 <= pair.row && pair.row < rows;
      boolean validColumn = 0 <= pair.column && pair.column < columns;
      return validRow && validColumn;
    }

    public RCPair fromLinearIndex(int n) {
      int r = n / columns;
      int c = n % columns;
      return RCPair.of(r, c, columns);
    }
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

  private static final class Graph {
    private final List<Integer>[] adj;
    final int vertices;
    private int edges;

    private Graph(int vertices) {
      this.vertices = vertices;
      this.adj = new List[vertices];
      Arrays.setAll(adj, i -> new ArrayList<>());
    }

    public void addEdge(int v, int w) {
      adj[v].add(w);
      adj[w].add(v);
      edges++;
    }

    public List<Integer> adj(int v) { return adj[v];}
  }

  private static final class CC {
    private final boolean[] visited;
    final int[] components;
    final int count;

    private CC(Graph graph, Map map) {
      visited = new boolean[graph.vertices];
      components = new int[graph.vertices];
      Arrays.fill(components, -1);

      int c = 0;
      for (int v = 0; v < graph.vertices; v++) {
        int i = map.valueAt(map.fromLinearIndex(v));
        if (!visited[v] && i == 1)
          dfs(graph, v, ++c);
      }

      count = c;
    }

    private void dfs(Graph graph, int vertex, int count) {
      visited[vertex] = true;
      components[vertex] = count;

      for (int neighbour : graph.adj(vertex)) {
        if (!visited[neighbour]) {
          dfs(graph, neighbour, count);
        }
      }
    }


  }

  private static void assertionStatus() {
    String status = IslandCount2.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }

  public static void main(String[] args) {
    assertionStatus();

    test(createMap1(), 1);
    test(createMap2(), 3);
  }

  private static void test(int[][] map, int expected) {
    CC cc = readMap(map);

    int numIslands = cc.count;
    System.out.println("# of islands: " + numIslands);
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
