package org.kogu.sedgewick_coursera.graph.undirected;

public interface EWGraph {
  int numberOfEdges();

  int numberOfVertices();

  Iterable<Edge> neighbours(int v);

  Iterable<Edge> allEdges();

  void addEdge(Edge e);
}
