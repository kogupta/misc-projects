package org.kogu.sedgewick_coursera.graph.directed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

public final class EWDigraph {
  private final int V;
  private final List<DirectedEdge>[] adj;
  private int E;

  public EWDigraph(int v) {
    V = v;
    adj = new List[V];
    Arrays.setAll(adj, i -> new ArrayList<>());
  }

  public void addEdge(DirectedEdge edge) {
    int from = edge.from();
    adj[from].add(edge);
    E++;
  }

  public Iterable<DirectedEdge> adjacencyListOf(int v) {
    return adj[v];
  }

  public int V() {return V;}
  public int E() {return E;}

  public Iterable<DirectedEdge> allEdges() {
    List<DirectedEdge> allEdges = new ArrayList<>(E);
    for (List<DirectedEdge> edges : adj)
      allEdges.addAll(edges);

    return allEdges;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(V).append(" vertices, ").append(E).append(" edges").append(lineSeparator());
    for (int i = 0, len = adj.length; i < len; i++) {
      List<DirectedEdge> edges = adj[i];
      String s = edges.stream()
          .map(e -> "->" + e.to() + ", " + e.weight())
          .collect(joining(" | "));
      sb.append(i).append(':').append(s).append(lineSeparator());
    }

    return sb.toString();
  }
}
