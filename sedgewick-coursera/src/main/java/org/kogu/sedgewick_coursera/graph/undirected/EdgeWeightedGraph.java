package org.kogu.sedgewick_coursera.graph.undirected;

import org.kogu.sedgewick_coursera.Iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EdgeWeightedGraph {
  private final int V;
  private final List<Edge>[] adj;
  private int E;

  public EdgeWeightedGraph(int v) {
    V = v;
    adj = new List[v];
    Arrays.setAll(adj, i -> new ArrayList<>());
  }

  public int E() { return E;}

  public int V() { return V;}

  public Iterable<Edge> adj(int v) { return adj[v];}

  public Iterable<Edge> edges() {
    return Iterables.concat(adj);
  }

  public void addEdge(Edge e) {
    int v = e.either(), w = e.other(v);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }
}
