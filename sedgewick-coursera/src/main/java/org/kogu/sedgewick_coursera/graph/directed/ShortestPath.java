package org.kogu.sedgewick_coursera.graph.directed;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;

/**
 * Single source shortest path:
 * find the shortest path from a source vertex to all other vertices
 */
public final class ShortestPath {
  private final EWDigraph digraph;
  private final int source;
  private final double[] distTo; // contains cumulative distance/weight
  private final DirectedEdge[] edgeTo;

  public ShortestPath(EWDigraph digraph, int source) {
    this.digraph = digraph;
    this.source = source;

    distTo = new double[digraph.V()];
    Arrays.fill(distTo, Double.POSITIVE_INFINITY);

    edgeTo = new DirectedEdge[digraph.V()];
  }

  public Iterable<DirectedEdge> pathTo(int v) {
    if (!hasPathTo(v)) {return Collections.emptyList();}

    Deque<DirectedEdge> stack = new ArrayDeque<>();
    for (DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()])
      stack.push(edge);

    return stack;
  }

  public double distTo(int v) {
    return distTo[v];
  }

  public boolean hasPathTo(int v) {
    return edgeTo[v] != null;
  }

  private void relax(DirectedEdge edge) {
    int v = edge.from(), w = edge.to();
    double existingWeight = distTo[w];
    double newWeight = distTo[v] + edge.weight();
    if (newWeight < existingWeight) {
      // update state
      distTo[w] = newWeight;
      edgeTo[w] = edge;
    }
  }
}
