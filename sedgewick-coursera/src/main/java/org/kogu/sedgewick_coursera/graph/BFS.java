package org.kogu.sedgewick_coursera.graph;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.kogu.sedgewick_coursera.graph.undirected.UndirectedGraph;

import java.util.*;

import static java.lang.System.out;

public final class BFS {
  private final Graph graph;
  private final boolean[] visited;
  private final int[] from;
  private final int start;

  public BFS(Graph graph, int start) {
    this.graph = graph;
    this.start = start;

    visited = new boolean[graph.vertices()];
    from = new int[graph.vertices()];
    Arrays.fill(from, -1);

    bfs(start);
  }

  private void bfs(int start) {
    Queue<Integer> queue = new ArrayDeque<>(graph.vertices());

    queue.add(start);
    visited[start] = true;

    while (!queue.isEmpty()) {
      int v = queue.remove();

      IntArrayList neighbours = graph.adjacencyListOf(v);
      for (int i = 0; i < neighbours.size(); i++) {
        int neighbour = neighbours.getInt(i);
        if (!visited[neighbour]) {
          queue.add(neighbour);
          visited[neighbour] = true;
          from[neighbour] = v;
        }
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
    BFS bfs = new BFS(graph, 0);
    System.out.println("path: " + Arrays.toString(bfs.from));

    for (Integer idx : bfs.pathTo(5)) {
      System.out.print(idx + " ");
    }

    System.out.println();
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = BFS.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>

}
