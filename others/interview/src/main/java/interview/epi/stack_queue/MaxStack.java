package interview.epi.stack_queue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;

import static interview.epi.array.SortFunctions.shuffle;
import static java.lang.System.out;
import static java.util.function.IntUnaryOperator.identity;

public class MaxStack<T extends Comparable<T>> implements Iterable<T> {
  private final Deque<T> stack;
  private final Deque<MaxCount<T>> aux; // auxiliary stack to maintain max distribution

  public MaxStack() {
    stack = new ArrayDeque<>();
    aux = new ArrayDeque<>();
  }

  public T pop() {
    T t = stack.pop();

    int cmp = t.compareTo(max());
    if (cmp == 0) {
      MaxCount<T> mc = aux.pop().dec();
      if (mc.count > 0) { aux.push(mc);}
    }
    return t;
  }

  public void push(@NotNull T t) {
    if (aux.isEmpty()) {
      MaxCount<T> mc = MaxCount.of(t);
      aux.push(mc);
    } else {
      T currMax = max();
      int cmp = currMax.compareTo(t);
      if (cmp == 0) aux.push(aux.pop().inc());
      else if (cmp < 0) aux.push(MaxCount.of(t));
    }
    stack.push(t);
  }

  public T max() { return aux.peek().maxValue; }

  public boolean isEmpty() {
    return stack.isEmpty();
  }

  @NotNull
  @Override
  public Iterator<T> iterator() {
    return stack.iterator();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (T t : stack) sb.append(t).append(' ');

    return sb.toString();
  }

  private static final class MaxCount<T> {
    final T maxValue;
    int count; // how many times it occurs

    MaxCount(T maxValue) {
      this.maxValue = maxValue;
      count = 1;
    }

    public MaxCount<T> inc() {
      count++;
      return this;
    }

    public MaxCount<T> dec() {
      count--;
      return this;
    }

    @Override
    public String toString() {
      return "[" + maxValue + ", " + count + "]";
    }

    public static <T> MaxCount<T> of(@NotNull T t) {
      return new MaxCount<>(t);
    }
  }

  //region test
  public static void main(String[] args) {
    _assertionStatus();

    MaxStack<Integer> stack = new MaxStack<>();
    for (int i : shuffledNumbers(10)) {
      System.out.println("push " + i);
      stack.push(i);
      System.out.println("stack: " + stack + ", aux: " + stack.aux);
    }

    System.out.println("stack: " + stack);
    System.out.println("aux: " + stack.aux);
    System.out.println("size: " + stack.stack.size());

    while (!stack.isEmpty()) {
      System.out.printf("max: %d,  pop: %d%n", stack.max(), stack.pop());
    }
  }

  private static int[] shuffledNumbers(int n) {
    int[] xs = new int[n];
    Arrays.setAll(xs, identity());
    shuffle(xs);
    System.out.println("numbers: " + Arrays.toString(xs));
    return xs;
  }

  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = MaxStack.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>
  //endregion
}
