package org.kogu.sedgewick_coursera;

import static java.lang.System.out;

// copied from: jdk.nashorn.internal.IntDeque
public final class IntStack implements IntIterable {
  private int[] deque;
  private int nextFree;

  private IntStack(int expectedSize) {
    deque = new int[expectedSize];
    nextFree = 0;
  }

  public void push(int value) {
    if (this.nextFree == this.deque.length) {
      int[] newDeque = new int[this.nextFree * 2];
      System.arraycopy(this.deque, 0, newDeque, 0, this.nextFree);
      this.deque = newDeque;
    }

    this.deque[this.nextFree++] = value;
  }

  public int pop() {
    return this.deque[--this.nextFree];
  }

  public int peek() {
    return this.deque[this.nextFree - 1];
  }

  public boolean isEmpty() { return this.nextFree == 0;}

  public void reset() { nextFree = 0; }

  public static IntStack withExpectedSize(int expectedSize) {
    return new IntStack(expectedSize);
  }

  @Override
  public IntIterator iterator() {
    return new DequeIterator(this);
  }

  private static final class DequeIterator implements IntIterator {
    private final int[] deque;
    private int index;

    private DequeIterator(IntStack deque) {
      this.deque = deque.deque;
      this.index = deque.nextFree - 1;
    }

    @Override public int nextInt() { return deque[index--];}

    @Override public boolean hasNext() { return index >= 0;}
  }

  public static void main(String[] args) {
    IntStack deque = IntStack.withExpectedSize(10);
    for (int i = 0; i < 10; i++) deque.push(i);

    out.println("-- iteration --");
    deque.iterator().forEachRemainingInt(n -> out.print(n + " "));
    out.println();

    out.println("-- peek/pop --");
    while (!deque.isEmpty())
      out.print(deque.pop() + " ");

  }
}
