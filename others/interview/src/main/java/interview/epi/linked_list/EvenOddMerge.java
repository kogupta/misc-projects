package interview.epi.linked_list;

import java.util.Arrays;
import java.util.List;

import static interview.epi.linked_list.IntLinkedList.of;

public final class EvenOddMerge {
  public static void main(String[] args) {
    IntLinkedList list = of(0, 1, 2, 3, 4, 5, 6);
    System.out.println(list);

    modify(list);

    System.out.println(list);
  }

  private static void modify(IntLinkedList list) {
    IntNode dummyOddHead = IntNode.of(-1);
    IntNode dummyEvenHead = IntNode.of(-2);
    List<IntNode> tails = Arrays.asList(dummyEvenHead, dummyOddHead);
    int even = 0;

    for (IntNode node = list.head; node != null; node = node.next) {
      tails.get(even).next = node;
      tails.set(even, tails.get(even).next);
      even ^= 1;
    }

    tails.get(1).next = null;
    tails.get(0).next = dummyOddHead.next;
  }
}
