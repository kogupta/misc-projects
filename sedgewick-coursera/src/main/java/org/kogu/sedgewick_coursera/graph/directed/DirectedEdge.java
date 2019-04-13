package org.kogu.sedgewick_coursera.graph.directed;

public final class DirectedEdge {
  private final int from, to;
  private final double weight;

  public DirectedEdge(int from, int to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public int from() { return from;}

  public int to() { return to;}

  public double weight() { return weight;}

  @Override
  public String toString() {
    return String.format("%d->%d, %,2f", from, to, weight);
  }
}
