package org.kogu.sort_test;

import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class String2Bytes {
  private final byte[] bytes;
  private final ByteBuffer out;
  private final CharsetEncoder ENCODER;
  private final CharBuffer charBuffer;

  public String2Bytes(int stringSize) {
    Preconditions.checkArgument(stringSize > 0);

    bytes = new byte[stringSize];
    out = ByteBuffer.wrap(bytes);
    ENCODER = StandardCharsets.US_ASCII.newEncoder();
    charBuffer = CharBuffer.allocate(stringSize);
  }

  public byte[] extractBytes(String s) {
    charBuffer.put(s);
    charBuffer.flip();
    CoderResult result = ENCODER.encode(charBuffer, out, true);
    System.out.println("---- " + result + " ----");

    byte[] array = out.array();

    out.flip();
    charBuffer.flip();

    return array;
  }

  public static void main(String[] args) {
    String2Bytes extractor = new String2Bytes(3);

    String[] xs = {"aaa", "bbb", "ccc", "ddd"};

    for (int i = 0; i < xs.length; i++) {
      String s = xs[i];
      byte[] array = extractor.extractBytes(s);

      System.out.printf("Are the arrays same? %s%n", extractor.bytes == array);

      String x = new String(array, StandardCharsets.US_ASCII);
      System.out.printf("Are the strings equal? %s%n", s.equals(x));
      System.out.println("Obtained: " + x);
      System.out.println("Expected: " + s);
      System.out.println();
    }

    System.out.println(UUID.randomUUID().toString().length());
  }

}
