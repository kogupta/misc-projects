package interview.epi.linked_list;

public final class RemoveDuplicates {
  public static void main(String[] args) {
    IntLinkedList xs = IntLinkedList.of(1, 1, 2, 3, 3, 3, 4, 5, 6, 7);
    System.out.println(xs);

    uniques(xs);
    System.out.println(xs);
  }

  private static void uniques(IntLinkedList xs) {
    IntNode head = xs.head;
    while (head != null) {
      IntNode next = head.next;
      while (next != null && next.n == head.n) {
        next = next.next;
      }
      head.next = next;
      head = next;
    }
  }
}
