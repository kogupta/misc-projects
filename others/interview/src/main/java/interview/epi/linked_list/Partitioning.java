package interview.epi.linked_list;

import interview.epi.array.SortFunctions;

import java.util.Arrays;

import static java.util.function.IntUnaryOperator.identity;

public final class Partitioning {
  public static void main(String[] args) {
    IntLinkedList list = IntLinkedList.of(shuffledInts());
    System.out.println(list);

    int k = 7;
    IntNode lessHd = IntNode.of(-1), lessItr = lessHd;
    IntNode eqHd = IntNode.of(-1), eqItr = eqHd;
    IntNode gtHd = IntNode.of(-1), gtItr = gtHd;
    for (IntNode node = list.head; node != null; node = node.next) {
      if (node.n < k) {
        lessItr.next = node;
        lessItr = lessItr.next;
      } else if (node.n == k) {
        eqItr.next = node;
        eqItr = eqItr.next;
      } else {
        gtItr.next = node;
        gtItr = gtItr.next;
      }
    }

    gtItr.next = null;
    lessItr.next = eqHd.next;
    eqItr.next = gtHd.next;

    System.out.println(lessHd.next);
  }

  private static int[] shuffledInts() {
    int[] ints = new int[12];
    Arrays.setAll(ints, identity());
    SortFunctions.shuffle(ints);
    System.out.println(Arrays.toString(ints));
    return ints;
  }
}
