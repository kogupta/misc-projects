package org.kogu.sedgewick_coursera.graph;

import org.kogu.sedgewick_coursera.graph.directed.Digraph;
import org.kogu.sedgewick_coursera.graph.undirected.UndirectedGraph;

import java.util.function.IntFunction;

public final class SampleGraphs {
  private SampleGraphs() {}

  public static Graph example(IntFunction<Graph> factory) {
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

  public static UndirectedGraph undirectedGraph(IntFunction<UndirectedGraph> factory) {
    UndirectedGraph graph = factory.apply(13);

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

  public static Digraph directedGraph(IntFunction<Digraph> constructor) {
    Digraph digraph = constructor.apply(13);
    digraph.addEdge(4, 2);
    digraph.addEdge(2, 3);
    digraph.addEdge(3, 2);
    digraph.addEdge(6, 0);
    digraph.addEdge(0, 1);
    digraph.addEdge(2, 0);
    digraph.addEdge(11, 12);
    digraph.addEdge(12, 9);
    digraph.addEdge(9, 10);
    digraph.addEdge(9, 11);
    digraph.addEdge(7, 9);
    digraph.addEdge(10, 12);
    digraph.addEdge(11, 4);
    digraph.addEdge(4, 3);
    digraph.addEdge(3, 5);
    digraph.addEdge(6, 8);
    digraph.addEdge(8, 6);
    digraph.addEdge(5, 4);
    digraph.addEdge(0, 5);
    digraph.addEdge(6, 4);
    digraph.addEdge(6, 9);
    digraph.addEdge(7, 6);
    return digraph;
  }

  public static Digraph dag() {
    Digraph graph = new Digraph(13);
    graph.addEdge(0, 6);
    graph.addEdge(0, 1);
    graph.addEdge(0, 5);

    graph.addEdge(2, 3);
    graph.addEdge(2, 0);

    graph.addEdge(3, 5);

    graph.addEdge(5, 4);

    graph.addEdge(6, 9);
    graph.addEdge(6, 4);

    graph.addEdge(7, 6);

    graph.addEdge(8, 7);

    graph.addEdge(9, 10);
    graph.addEdge(9, 11);
    graph.addEdge(9, 12);

    graph.addEdge(11, 12);

    return graph;
  }
}
