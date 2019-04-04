package org.kogu.sedgewick_coursera;

// copied from: jdk.nashorn.internal.IntDeque
public final class IntDeque {
  private int[] deque;
  private int nextFree;

  private IntDeque(int expectedSize) {
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

  public int getAndIncrement() {
    int lastIdx = this.nextFree - 1;
    int lastItem = this.deque[lastIdx];
    this.deque[lastIdx] = this.deque[lastIdx] + 1;
    return lastItem;
  }

  public int decrementAndGet() {
    return --this.deque[this.nextFree - 1];
  }

  public boolean isEmpty() { return this.nextFree == 0;}

  public void reset() { nextFree = 0; }

  public static IntDeque withExpectedSize(int expectedSize) {
    return new IntDeque(expectedSize);
  }
}
