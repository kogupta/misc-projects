package org.kogu.sedgewick_coursera.graph;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public interface Graph {
  void addEdge(int v, int w);

  IntArrayList adjacencyListOf(int v);

  int vertices();

  int edges();
}
