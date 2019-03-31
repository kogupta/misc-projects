package interview.epi.stack_queue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

public final class Postings {
  // `hashCode` and `equals` are required for assertion equality!
  private static final class IntNode {
    final int data;
    int order;
    IntNode jump, next;

    IntNode(int data) {
      this.data = data;
      order = -1;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      IntNode intNode = (IntNode) o;
      return data == intNode.data && order == intNode.order;
    }

    @Override
    public int hashCode() {
      int result = data;
      result = 31 * result + order;
      return result;
    }
  }

  public static void main(String[] args) {
    _assertionStatus();

    List<IntNode> xs = displayPath(createNodes(), Postings::path);
    List<IntNode> ys = displayPath(createNodes(), Postings::iterativePath);

    assert xs.equals(ys);
  }

  @NotNull
  private static IntNode createNodes() {
    IntNode a = new IntNode(1);
    IntNode b = new IntNode(2);
    IntNode c = new IntNode(3);
    IntNode d = new IntNode(4);
    a.jump = c;
    a.next = b;
    b.jump = d;
    b.next = c;
    c.jump = b;
    c.next = d;
    d.jump = d;
    return a;
  }

  private static List<IntNode> displayPath(IntNode a,
                                           Function<IntNode, List<IntNode>> finder) {
    List<IntNode> path = finder.apply(a);
    String s = path.stream()
        .map(p -> String.valueOf(p.data))
        .collect(joining("->"));

    System.out.println(s);
    return path;
  }

  private static List<IntNode> path(IntNode start) {
    Pair pair = helper(start, new Pair(new ArrayList<>(), 0));
    return pair.path;
  }

  private static Pair helper(IntNode node, Pair acc) {
    if (node == null || node.order != -1) { return acc; }

    acc.path.add(node);
    node.order = acc.order;
    Pair pair = helper(node.jump, acc.incOrder());
    return helper(node.next, pair);
  }

  private static final class Pair {
    final List<IntNode> path;
    final int order;

    private Pair(List<IntNode> path, int order) {
      this.path = path;
      this.order = order;
    }

    Pair incOrder() {
      return new Pair(path, order + 1);
    }
  }

  private static List<IntNode> iterativePath(IntNode start) {
    List<IntNode> path = new ArrayList<>();

    Deque<IntNode> stack = new ArrayDeque<>();
    int order = 0;

    stack.push(start);

    while (!stack.isEmpty()) {
      IntNode node = stack.pop();
      if (node != null && node.order == -1) {
        node.order = order++;
        path.add(node);

        // array deque does not like null elements!
        if (node.next != null) stack.push(node.next);
        if (node.jump != null) stack.push(node.jump);
      }
    }

    return path;
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = Postings.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>
}
