package org.kogu.collections.maps;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Taken from FastUtil `it.unimi.dsi.fastutil.HashCommon` implementation
 */
public enum Tools {
  ;

  /**
   * Return the least power of two greater than or equal to the specified value.
   * <p>
   * <p>Note that this function will return 1 when the argument is 0.
   *
   * @param x a long integer smaller than or equal to 2<sup>62</sup>.
   * @return the least power of two greater than or equal to the specified value.
   */
  public static long nextPowerOfTwo(long x) {
    if (x == 0) return 1;
    x--;
    x |= x >> 1;
    x |= x >> 2;
    x |= x >> 4;
    x |= x >> 8;
    x |= x >> 16;
    return (x | x >> 32) + 1;
  }

  /**
   * Returns the least power of two smaller than or equal to 2<sup>30</sup> and larger than or equal to <code>Math.ceil( expected / f )</code>.
   *
   * @param expected the expected number of elements in a hash table.
   * @param f        the load factor.
   * @return the minimum possible size for a backing array.
   * @throws IllegalArgumentException if the necessary size is larger than 2<sup>30</sup>.
   */
  public static int arraySize(final int expected, final float f) {
    final long s = Math.max(2, nextPowerOfTwo((long) Math.ceil(expected / f)));
    if (s > (1 << 30))
      throw new IllegalArgumentException("Too large (" + expected + " expected elements with load factor " + f + ")");
    return (int) s;
  }

  //taken from FastUtil
  /**
   * 2<sup>32</sup> &middot; &phi;, &phi; = (&#x221A;5 &minus; 1)/2.
   */
  private static final int INT_PHI = 0x9E3779B9;

  public static int phiMix(final int x) {
    final int h = x * INT_PHI;
    return h ^ (h >> 16);
  }

  // copied from JDK j.u.c.HashMap
  private static final int MAXIMUM_CAPACITY = 1 << 30;

  public static int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
  }


  public static void debug(int[] keys, int[] values, boolean[] used) {
    checkArgument(keys.length == values.length);
    checkArgument(values.length == used.length);


  }

}