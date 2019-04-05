package org.kogu.sedgewick_coursera.graph.directed;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.kogu.sedgewick_coursera.IntIterator;
import org.kogu.sedgewick_coursera.IntStack;
import org.kogu.sedgewick_coursera.graph.SampleGraphs;

import java.util.function.IntConsumer;

public final class TopologicalSort {
  private final IntStack order; // reverse post-order dfs
  private final boolean[] visited;

  // order in which nodes are visited
  private final IntArrayFIFOQueue preOrder;
  // order in which nodes are completed
  private final IntArrayFIFOQueue postOrder;

  public TopologicalSort(Digraph digraph) {
    order = IntStack.withExpectedSize(digraph.vertices());
    visited = new boolean[digraph.vertices()];
    preOrder = new IntArrayFIFOQueue(digraph.vertices());
    postOrder = new IntArrayFIFOQueue(digraph.vertices());

    for (int v = 0; v < digraph.vertices(); v++) {
      if (!visited[v]) {
        dfs(digraph, v);
      }
    }
  }

  private void dfs(Digraph digraph, int vertex) {
    preOrder.enqueue(vertex);

    visited[vertex] = true;
    IntArrayList neighbours = digraph.adjacencyListOf(vertex);
    for (int i = 0; i < neighbours.size(); i++) {
      int neighbour = neighbours.getInt(i);
      if (!visited[neighbour])
        dfs(digraph, neighbour);
    }

    order.push(vertex);

    postOrder.enqueue(vertex);
  }

  public IntIterator orderOfVertices() {
    return order.iterator();
  }

  public static void main(String[] args) {
    Digraph dag = SampleGraphs.dag();
    System.out.println(dag);
    TopologicalSort sort = new TopologicalSort(dag);

    System.out.println("-- order of nodes reached --");
    iterate(sort.preOrder, n -> System.out.print(n + " -> "));
    System.out.println(); System.out.println();

    System.out.println("-- order of nodes processed --");
    iterate(sort.postOrder, n -> System.out.print(n + " -> "));
    System.out.println(); System.out.println();

    sort.orderOfVertices().forEachRemainingInt(n -> System.out.print(n + " -> "));
  }

  private static void iterate(IntArrayFIFOQueue queue, IntConsumer c) {
    while (!queue.isEmpty()) c.accept(queue.dequeueInt());
  }
}
