package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

// TODO: missing array index checks
// assumption is ACTUALLY a DAG - no checks in code
// written in IntelliJ not in browser!
public final class DominoDataLabsDAG {
  public static void main(String[] args) {
    DirectedGraph graph = null;
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      String[] split = line.split(", ");
      if (split.length == 1) {
        // vertices
        int V = parseInt(split[0]);
        graph = new DirectedGraph(V);
      } else if (split.length == 2) {
        // edge definitions
        int from = parseInt(split[0]), to = parseInt(split[1]);
        if (graph != null) {
          // graph should be initialized
          graph.addEdge(from, to);
        }
      }
    }

//    System.out.println(graph);

    assert graph != null : "Graph not yet initialized - what are we doing here!";

    List<Vertex> roots = Arrays.stream(graph.vertexContainer)
        .filter(vertex -> vertex.inDegree == 0)
        .collect(Collectors.toList());
//    System.out.println("nodes with 0 in degree: " + roots);

    for (Vertex vertex : roots) {
      int v = vertex.index;
      DFS sort = new DFS(graph, v);

      // avoid optimization by jit
      if (sort.hashCode() == Integer.MIN_VALUE) {
        System.out.println();
      }
    }
  }

  private static final class DirectedGraph {
    final int vertices; // # of vertices
    private final Vertex[] vertexContainer;
    private final List<Integer>[] adj;
    private int E; // number of edges
    // remove edges -> not being used

    DirectedGraph(int v) {
      if (v < 0) throw new IllegalArgumentException("Invalid # of vertices");

      vertices = v;
      adj = new List[vertices];
      Arrays.setAll(adj, i -> new ArrayList<>());
      vertexContainer = new Vertex[v];
      Arrays.setAll(vertexContainer, Vertex::new);
    }

    void addEdge(int from, int to) {
      adj[from].add(to);
      E++;
      vertexContainer[from].incOutDegree();
      vertexContainer[to].incInDegree();
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("vertices: ").append(vertices).append(", edges: ").append(E).append(lineSeparator());
      for (int i = 0; i < adj.length; i++) {
        List<Integer> neighbours = adj[i];
        final int i2 = i;
        String edges = neighbours.stream().
            map(n -> i2 + "->" + n).
            collect(joining(", ", "[", "]"));
        sb.append(edges).append(lineSeparator());
      }

      return sb.toString();
    }

    List<Integer> adjacencyListOf(int vertex) {
      return adj[vertex];
    }
  }

  // TODO: ideally use stack rather than recursion for traversal
  private static final class DFS {
    private final List<Integer> path;
    private final boolean[] visited;

    DFS(DirectedGraph digraph, int v) {
      path = new ArrayList<>(digraph.vertices);
      visited = new boolean[digraph.vertices];

      dfs(digraph, v);
    }

    private void dfs(DirectedGraph digraph, int vertex) {
      visited[vertex] = true;
      path.add(vertex);

      List<Integer> neighbours = digraph.adjacencyListOf(vertex);
      if (neighbours.isEmpty()) {
        String s = path.stream().map(n -> Integer.toString(n)).collect(joining("->"));
        System.out.println(s);
        deleteLast(path);
        visited[vertex] = false;
      } else {
        for (int neighbour : neighbours) {
          if (!visited[neighbour])
            dfs(digraph, neighbour);
        }

        deleteLast(path);
        visited[vertex] = false;
      }
    }
  }

  private static final class Vertex {
    final int index;
    private int inDegree, outDegree;
//    final List<Integer> adjacencyList;

    private Vertex(int index) {
      this.index = index;
//      adjacencyList = new ArrayList<>();
    }

//    public void addEdge(int to) {
//      adjacencyList.add(to);
//      incOutDegree();
//    }

    void incInDegree() { inDegree++;}

    void incOutDegree() { outDegree++;}

    @Override
    public String toString() {
      return String.format("Vertex[%d in:%d, out:%d]", index, inDegree, outDegree);
    }
  }

  private static <T> void deleteLast(List<T> xs) {
    if (!xs.isEmpty()) {
      int last = xs.size() - 1;
      xs.remove(last);
    }
  }
}
