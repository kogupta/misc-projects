package interview.epi.linked_list;

public final class LinkedList<T> {
  Node<T> head;

  @Override
  public String toString() {
    return head == null ? "-" : head.toString();
  }
}
