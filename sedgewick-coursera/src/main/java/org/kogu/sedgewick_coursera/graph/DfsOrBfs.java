package org.kogu.sedgewick_coursera.graph;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.kogu.sedgewick_coursera.graph.undirected.UndirectedGraph;

import java.util.*;

import static java.lang.System.out;
import static java.util.Collections.asLifoQueue;

public final class DfsOrBfs {
  private final Graph graph;
  private final boolean[] visited;
  private final int[] from;
  private final int start;
  private final Queue<Integer> queue;

  public DfsOrBfs(Graph graph, int sourceVertex, Queue<Integer> queue) {
    this.graph = graph;
    this.start = sourceVertex;
    this.queue = queue;
    visited = new boolean[graph.vertices()];
    from = new int[graph.vertices()];
    Arrays.fill(from, -1);

    dfs2(sourceVertex);
  }

  private void dfs2(int w) {
    queue.add(w);
    visited[w] = true;

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

    System.out.println("== bfs ==");
    _check(graph, new ArrayDeque<>(graph.vertices()));

    System.out.println("-- dfs --");
    _check(graph, asLifoQueue(new ArrayDeque<>(graph.vertices())));
  }

  private static void _check(Graph graph, Queue<Integer> queue) {
    DfsOrBfs dfs = new DfsOrBfs(graph, 0, queue);
    System.out.println("path: " + Arrays.toString(dfs.from));

    for (Integer idx : dfs.pathTo(3)) {
      System.out.print(idx + " ");
    }

    System.out.println();
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = DfsOrBfs.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>

}
