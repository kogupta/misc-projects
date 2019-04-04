package org.kogu.sedgewick_coursera.graph.directed;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.kogu.sedgewick_coursera.graph.Graph;

import java.util.Arrays;

import static java.lang.System.lineSeparator;
import static java.lang.System.out;

public final class Digraph implements Graph {
  private final int V; // # of vertices
  private int E;       // # of edges
  private final IntArrayList[] adj; // adjacency list for each vertex

  public Digraph(int v) {
    V = v;
    adj = new IntArrayList[V];
    Arrays.setAll(adj, i -> new IntArrayList());
  }

  @Override
  public void addEdge(int v, int w) {
    adj[v].add(w);
    E++;
  }

  @Override
  public IntArrayList adjacencyListOf(int v) { return adj[v];}

  @Override
  public int vertices() { return V;}

  @Override
  public int edges() { return E;}

  public Digraph reverse() {
    Digraph reverse = new Digraph(V);
    for (int v = 0; v < V; v++) {
      IntArrayList neighbours = adj[v];
      for (int i = 0; i < neighbours.size(); i++) {
        int neighbour = neighbours.getInt(i);
        reverse.addEdge(neighbour, v);
      }
    }

    return reverse;
  }

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

    Digraph graph = Graph.directedGraph(Digraph::new);
    System.out.println(graph);

    out.println("--- reverse ---");
    out.println(graph.reverse());
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = Digraph.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>

}
