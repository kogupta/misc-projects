package interview;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * A type-specific {@link Iterator}; provides an additional method to avoid
 * (un)boxing, and the possibility to skip elements.
 *
 * @see Iterator
 */
public interface IntIterator extends PrimitiveIterator.OfInt {
  /**
   * Returns the next element as a primitive type.
   *
   * @return the next element in the iteration.
   * @see Iterator#next()
   */
  @Override
  int nextInt();
  /**
   * {@inheritDoc}
   *
   * @deprecated Please use the corresponding type-specific method instead.
   */
  @Deprecated
  @Override
  default Integer next() {
    return nextInt();
  }
  /**
   * {@inheritDoc}
   *
   * @deprecated Please use the corresponding type-specific method instead.
   */
  @Deprecated
  @Override
  default void forEachRemaining(final Consumer<? super Integer> action) {
    forEachRemaining((IntConsumer) action::accept);
  }

  default void forEachRemainingInt(final IntConsumer action) {
    forEachRemaining(action);
  }

  /**
   * Skips the given number of elements.
   *
   * <p>
   * The effect of this call is exactly the same as that of calling
   * {@link #next()} for {@code n} times (possibly stopping if {@link #hasNext()}
   * becomes false).
   *
   * @param n
   *            the number of elements to skip.
   * @return the number of elements actually skipped.
   * @see Iterator#next()
   */
  default int skip(final int n) {
    if (n < 0)
      throw new IllegalArgumentException("Argument must be nonnegative: " + n);
    int i = n;
    while (i-- != 0 && hasNext())
      nextInt();
    return n - i - 1;
  }
}

