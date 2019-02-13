package org.kogu.misc;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * misc checks/tests
 */
public class $$Misc$$ {
  @Test
  public void surroundWithParens() {
    int[] ints = IntStream.range(0, 4).toArray();
    StringBuilder sb = new StringBuilder();
    sb.append('(');
    for (int n : ints)
      sb.append(n).append(',');

    sb.deleteCharAt(sb.lastIndexOf(","));
    sb.append(')');

    String expected = "(0,1,2,3)";
    String obtained = sb.toString();

    assertEquals(expected, obtained);
  }
}
