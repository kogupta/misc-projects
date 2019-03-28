package interview.epi.linked_list;

import static interview.epi.linked_list.IntLinkedList.of;

public final class MergeSortedList {
  public static void main(String[] args) {
    _assertionStatus();

    IntLinkedList as = of(1, 2, 3, 4);
    IntLinkedList bs = of(5, 6, 7, 8);

    System.out.println(merge(of(), of()));
    System.out.println(merge(of(), as));
    System.out.println(merge(as, of()));
    System.out.println(merge(as, bs));
  }

  private static IntLinkedList merge(IntLinkedList as, IntLinkedList bs) {
    IntLinkedList result = new IntLinkedList();
    IntNode dummy = IntNode.of(0);
    IntNode current = dummy;

    while (!as.isEmpty() && !bs.isEmpty()) {
      if (as.head() <= bs.head()) {
        current.next = as.head;
        as = as.tail();
      } else {
        current.next = bs.head;
        bs = bs.tail();
      }

      current = current.next;
    }

    current.next = as.isEmpty() ? bs.head : as.head;

    result.head = dummy.next;
    return result;
  }



  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = MergeSortedList.class.desiredAssertionStatus() ? "enabled" : "disabled";
    System.out.println("Assertion: " + status);
    System.out.println("====================");
  }
  //</editor-fold>

}
