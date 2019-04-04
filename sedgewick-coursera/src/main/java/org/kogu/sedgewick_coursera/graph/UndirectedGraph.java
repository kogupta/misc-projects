package org.kogu.sedgewick_coursera.graph;

import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.Arrays;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

public final class UndirectedGraph implements Graph {
  private final int V; // # of vertices
  private int E;       // # of edges
  private final IntArrayList[] adj; // adjacency list for each vertex

  public UndirectedGraph(int v) {
    V = v;
    adj = new IntArrayList[V];
    Arrays.setAll(adj, i -> new IntArrayList());
  }

  @Override
  public void addEdge(int v, int w) {
    adj[v].add(w);
    adj[w].add(v);
    E++;
  }

  @Override
  public IntArrayList adjacencyListOf(int v) { return adj[v];}

  @Override
  public int vertices() { return V;}

  @Override
  public int edges() { return E;}

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(V).append(" vertices, ").append(E).append(" edges").append(lineSeparator());
    for (int v = 0; v < V; v++) {
      sb.append(v).append(": ").append(adj[v].toString()).append(lineSeparator());
    }

    return sb.toString();
  }

  public static void main(String... args) {
    _assertionStatus();

    Graph graph = Graph.example(UndirectedGraph::new);
    System.out.println(graph);

  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = UndirectedGraph.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>

}
