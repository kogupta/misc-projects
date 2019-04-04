package org.kogu.sedgewick_coursera.graph;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.kogu.sedgewick_coursera.IntDeque;

import java.util.Arrays;

import static java.lang.System.out;

public final class ConnectedComponents {
  private final Graph graph;
  private final boolean[] visited;
  private final int[] components;
  private final int count;

  public ConnectedComponents(Graph graph) {
    this.graph = graph;
    visited = new boolean[graph.vertices()];
    components = new int[graph.vertices()];
    Arrays.fill(components, -1);

    IntDeque stack = IntDeque.withExpectedSize(graph.vertices());
    int numComponents = 0;
    for (int v = 0; v < graph.vertices(); v++) {
      if (!visited[v]) {
        dfs(v, stack, numComponents++);
        stack.reset();
      }
    }

    this.count = numComponents;
  }

  private void dfs(int v, IntDeque stack, int count) {
    stack.push(v);
    components[v] = count;
    visited[v] = true;

    while (!stack.isEmpty()) {
      int vertex = stack.pop();

      IntArrayList adjacencyListOf = graph.adjacencyListOf(vertex);
      for (int i = 0; i < adjacencyListOf.size(); i++) {
        int neighbour = adjacencyListOf.getInt(i);
        if (!visited[neighbour]) {
          stack.push(neighbour);
          components[neighbour] = count;
          visited[neighbour] = true;
        }
      }
    }
  }

  public boolean isConnected(int v, int w) {
    return components[v] == components[w];
  }

  public int componentId(int v) {
    return components[v];
  }

  public int count() {
    return count;
  }

  public Int2ObjectMap<IntArrayList> components() {
    Int2ObjectMap<IntArrayList> result = new Int2ObjectLinkedOpenHashMap<>(count);
    for (int vertex = 0; vertex < components.length; vertex++) {
      int component = components[vertex];
      result.merge(component, new IntArrayList(), (integers, integers2) -> {
        integers.
      });
    }

  }

  public static void main(String... args) {
    _assertionStatus();

    Graph graph = Graph.tinyGraphExample(UndirectedGraph::new);
    System.out.println(graph);

    ConnectedComponents cc = new ConnectedComponents(graph);
    System.out.println("components: " + cc.count());
    System.out.println(Arrays.toString(cc.components));

  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = ConnectedComponents.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>

}
