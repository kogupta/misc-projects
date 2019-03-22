package func.prog.java;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface IntIterable extends Iterable<Integer> {
  @NotNull
  @Override
  IntIterator iterator();

  @Override
  default void forEach(Consumer<? super Integer> action) {
    forEachInt(n -> action.accept(n));
  }

  default void forEachInt(IntConsumer action) {
    for (IntIterator iterator = iterator(); iterator.hasNext(); ) {
      int n = iterator.nextInt();
      action.accept(n);
    }
  }
}
