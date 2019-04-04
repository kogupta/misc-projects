package org.kogu.sedgewick_coursera.graph;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.kogu.sedgewick_coursera.graph.undirected.UndirectedGraph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;

import static java.lang.System.out;

public final class DFS {
  private final Graph graph;
  private final boolean[] visited;
  private final int[] from;
  private final int start;

  public DFS(Graph graph, int w) {
    this.graph = graph;
    visited = new boolean[graph.vertices()];
    from = new int[graph.vertices()];
    Arrays.fill(from, -1);

    this.start = w;

    dfs(w);
  }

  private void dfs(int w) {
    visited[w] = true;
    IntArrayList adjacencyListOf = graph.adjacencyListOf(w);
    for (int i = 0; i < adjacencyListOf.size(); i++) {
      int v = adjacencyListOf.getInt(i);
      if (!visited[v]) {
        from[v] = w;
        dfs(v);
      }
    }
  }

  public boolean hasPathTo(int v) {
    return visited[v];
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v)) return Collections.emptyList();

    Deque<Integer> stack = new ArrayDeque<>(graph.vertices());

    for (int idx = v; idx != start; idx = from[idx]) {
      stack.push(idx);
    }

    stack.push(start);

    return stack;
  }

  public static void main(String... args) {
    _assertionStatus();

    Graph graph = Graph.example(UndirectedGraph::new);
    System.out.println(graph);
    DFS dfs = new DFS(graph, 0);
    System.out.println("path: " + Arrays.toString(dfs.from));

    for (Integer idx : dfs.pathTo(5)) {
      System.out.print(idx + " ");
    }

    System.out.println();
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = DFS.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>

}
