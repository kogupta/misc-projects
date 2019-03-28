package interview.epi.linked_list;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class IntNode implements Comparable<Integer> {
  int n;
  IntNode next;

  @Override
  public int compareTo(@NotNull Integer o) {
    return Integer.compare(n, o);
  }

  @Override
  public String toString() {
    return next == null ? String.valueOf(n) : (n + "->" + next.toString());
  }

  public static IntNode of(int n) {
    IntNode node = new IntNode();
    node.n = n;
    return node;
  }

  public static IntNode of(int a, int b) {
    IntNode head = IntNode.of(a);
    head.next = IntNode.of(b);
    return head;
  }

  public static IntNode of(int a, int b, int c) {
    IntNode head = IntNode.of(a);
    head.next = IntNode.of(b, c);
    return head;
  }

  public static IntNode of(int... xs) {
    List<IntNode> nodes = Arrays.stream(xs).mapToObj(IntNode::of).collect(Collectors.toList());
    Collections.reverse(nodes);
    for (int i = 1; i < nodes.size(); i++) {
      IntNode node = nodes.get(i);
      node.next = nodes.get(i - 1);
    }

    return nodes.get(nodes.size() - 1);
  }

  public static void main(String[] args) {
    _assertionStatus();
    System.out.println(IntNode.of(1));
    System.out.println(IntNode.of(1, 2));
    System.out.println(IntNode.of(1, 2, 3));
    System.out.println(IntNode.of(1, 2, 3, 4));
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = IntNode.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
  //</editor-fold>
}
