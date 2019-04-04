package org.kogu.sedgewick_coursera.graph;

import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.function.IntFunction;

interface Graph {
  void addEdge(int v, int w);
  IntArrayList adjacencyListOf(int v);
  int vertices();
  int edges();

  static Graph example(IntFunction<Graph> factory) {
    Graph graph = factory.apply(6);
    graph.addEdge(0, 2);
    graph.addEdge(0, 1);
    graph.addEdge(1, 2);
    graph.addEdge(3, 5);
    graph.addEdge(3, 4);
    graph.addEdge(2, 3);
    graph.addEdge(2, 4);
    graph.addEdge(0, 5);
    return graph;
  }

  static Graph tinyGraphExample(IntFunction<Graph> factory) {
    Graph graph = factory.apply(13);

    graph.addEdge(0, 6);
    graph.addEdge(0, 2);
    graph.addEdge(0, 1);

    graph.addEdge(3, 5);

    graph.addEdge(4, 5);
    graph.addEdge(4, 6);
    graph.addEdge(4, 3);

    graph.addEdge(5, 0);

    graph.addEdge(7, 8);

    graph.addEdge(9, 11);
    graph.addEdge(9, 10);

    graph.addEdge(11, 12);

    graph.addEdge(12, 9);

    return graph;
  }
}
