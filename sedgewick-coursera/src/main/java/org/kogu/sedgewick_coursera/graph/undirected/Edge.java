package org.kogu.sedgewick_coursera.graph.undirected;

import static java.lang.Double.compare;

public final class Edge implements Comparable<Edge> {
  private final int v, w;
  private final double weight;

  public Edge(int v, int w, double weight) {
    this.v = v;
    this.w = w;
    this.weight = weight;
  }

  public int either() { return v; }

  public int other(int vertex) { return vertex == v ? w : v; }

  @Override
  public int compareTo(Edge o) {
    return compare(weight, o.weight);
  }

  @Override
  public String toString() {
    return String.format("%d-%d, %,2f", v, w, weight);
  }
}
