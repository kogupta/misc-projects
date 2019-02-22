package org.kogu.collections.test_helper;

/**
 * An interface to be implemented by each performance test
 */
public interface IMapTest {
  public void setup(final int[] keys, final float fillFactor, final int oneFailureOutOf);

  public int test();
}
