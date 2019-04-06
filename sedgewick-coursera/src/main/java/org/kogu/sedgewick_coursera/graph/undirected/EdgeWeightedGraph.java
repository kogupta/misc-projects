package org.kogu.sedgewick_coursera.graph.undirected;

import org.kogu.sedgewick_coursera.Iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EdgeWeightedGraph implements EWGraph {
  private final int V;
  private final List<Edge>[] adj;
  private int E;

  public EdgeWeightedGraph(int v) {
    V = v;
    adj = new List[v];
    Arrays.setAll(adj, i -> new ArrayList<>());
  }

  @Override
  public int numberOfEdges() { return E;}

  @Override
  public int numberOfVertices() { return V;}

  @Override
  public Iterable<Edge> neighbours(int v) { return adj[v];}

  @Override public Iterable<Edge> allEdges() { return Iterables.concat(adj);}

  @Override
  public void addEdge(Edge e) {
    int v = e.either(), w = e.other(v);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }
}
