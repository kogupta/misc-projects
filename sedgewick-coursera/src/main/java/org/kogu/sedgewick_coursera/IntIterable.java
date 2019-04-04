package org.kogu.sedgewick_coursera;

import java.util.Objects;
import java.util.function.Consumer;

public interface IntIterable extends Iterable<Integer> {
  /**
   * Returns a type-specific iterator.
   *
   * <p>
   * Note that this specification strengthens the one given in
   * {@link Iterable#iterator()}.
   *
   * @return a type-specific iterator.
   * @see Iterable#iterator()
   */
  @Override
  IntIterator iterator();
  /**
   * Performs the given action for each element of this type-specific
   * {@link java.lang.Iterable} until all elements have been processed or the
   * action throws an exception.
   *
   * @param action
   *            the action to be performed for each element.
   * @see java.lang.Iterable#forEach(java.util.function.Consumer)
   * @since 8.0.0
   */
  default void forEachInt(final java.util.function.IntConsumer action) {
    Objects.requireNonNull(action);
    for (final IntIterator iterator = iterator(); iterator.hasNext();)
      action.accept(iterator.nextInt());
  }
  /**
   * {@inheritDoc}
   *
   * @deprecated Please use the corresponding type-specific method instead.
   */
  @Deprecated
  @Override
  default void forEach(final Consumer<? super Integer> action) {
    forEachInt(action::accept);
  }
}