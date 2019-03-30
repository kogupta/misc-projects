package interview.epi.linked_list;

import interview.IntIterator;

public final class IntLinkedList implements IntIterator {
  IntNode head;

  public int head() {
    assert head != null;

    return head.n;
  }

  public IntLinkedList tail() {
    IntLinkedList list = new IntLinkedList();
    list.head = head.next;
    return list;
  }

  public boolean isEmpty() {
    return head == null;
  }

  @Override
  public int nextInt() {
    int n = head.n;
    head = head.next;
    return n;
  }

  @Override
  public boolean hasNext() {
    return head != null;
  }

  @Override
  public String toString() {
    return head == null ? "-" : head.toString();
  }

  public static IntLinkedList of(int n) {
    IntLinkedList list = new IntLinkedList();
    list.head = IntNode.of(n);
    return list;
  }

  public static IntLinkedList of(int a, int b) {
    IntLinkedList list = new IntLinkedList();
    list.head = IntNode.of(a, b);
    return list;
  }

  public static IntLinkedList of(int a, int b, int c) {
    IntLinkedList list = new IntLinkedList();
    list.head = IntNode.of(a, b, c);
    return list;
  }

  public static IntLinkedList of(int... ns) {
    if (ns == null || ns.length == 0) return new IntLinkedList();

    IntLinkedList list = new IntLinkedList();
    list.head = IntNode.of(ns);
    return list;
  }

  public static void main(String[] args) {
    _assertionStatus();

    System.out.println(of(1));
    System.out.println(of(1, 2));
    System.out.println(of(1, 2, 3));
    System.out.println(of(1, 2, 3, 4));

    IntLinkedList list = of(1, 2, 3, 4);
    list.forEachRemainingInt(System.out::println);
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = IntLinkedList.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
  //</editor-fold>
}
