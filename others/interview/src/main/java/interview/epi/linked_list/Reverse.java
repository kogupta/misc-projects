package interview.epi.linked_list;

import static interview.epi.linked_list.IntLinkedList.of;

public final class Reverse {
  public static void main(String[] args) {
    _assertionStatus();

    check(of());
    check(of(1));
    check(of(1, 2));
    check(of(1, 2, 3));
    check(of(1, 2, 3, 4, 5, 6));
  }

  private static void check(IntLinkedList list) {
    System.out.println(list);
    reverse(list);
    System.out.println(list);
    System.out.println("------------------------");
  }

  private static void reverse(IntLinkedList list) {
    IntNode head = list.head;
    IntNode prev = null;
    while (head != null) {
      IntNode next = head.next;
      head.next = prev;

      prev = head;
      head = next;
    }

    list.head = prev;
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = Reverse.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
  //</editor-fold>

}
