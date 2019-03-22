package func.prog.java;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface IntIterator extends Iterator<Integer> {
  @Override
  boolean hasNext();

  @Override
  @Deprecated
  default Integer next() {
    return nextInt();
  }

  int nextInt();

  @Override
  default void forEachRemaining(Consumer<? super Integer> action) {
    forEachRemainingInt(n -> action.accept(n));
  }

  void forEachRemainingInt(IntConsumer intConsumer);
}
