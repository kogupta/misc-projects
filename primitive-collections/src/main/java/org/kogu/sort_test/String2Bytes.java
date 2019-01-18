package org.kogu.sort_test;

import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.util.UUID;

public final class String2Bytes {
  private final byte[] bytes;
  private final ByteBuffer out;
  private final CharsetEncoder encoder;
  private final CharsetDecoder decoder;
  private final CharBuffer charBuffer;

  public String2Bytes(int stringSize, Charset charset) {
    Preconditions.checkArgument(stringSize > 0);

    bytes = new byte[stringSize];
    out = ByteBuffer.wrap(bytes);
    charBuffer = CharBuffer.allocate(stringSize);
    encoder = charset.newEncoder();
    decoder = charset.newDecoder();
  }

  public byte[] extractBytes(String s) {
    charBuffer.put(s);
    charBuffer.flip();
    CoderResult result = encoder.encode(charBuffer, out, true);
//    System.out.println("---- " + result + " ----");

    byte[] array = out.array();

    out.flip();
    charBuffer.flip();

    return array;
  }

  public String readString(ByteBuffer buffer) {
    decoder.decode(buffer, charBuffer, true);
    char[] chars = charBuffer.array();
    charBuffer.flip();
    return new String(chars);
  }

  public static void main(String[] args) {
    Charset charset = StandardCharsets.US_ASCII;
    String2Bytes extractor = new String2Bytes(3, charset);

    String[] xs = {"aaa", "bbb", "ccc", "ddd"};

    for (int i = 0; i < xs.length; i++) {
      String s = xs[i];
      byte[] array = extractor.extractBytes(s);

      System.out.printf("Are the arrays same? %s%n", extractor.bytes == array);

      String x = new String(array, charset);
      System.out.printf("Are the strings equal? %s%n", s.equals(x));
      System.out.println("Obtained: " + x);
      System.out.println("Expected: " + s);

      System.out.print("Buffer to string: ");
      ByteBuffer buffer = ByteBuffer.wrap(array);
      System.out.println("obtained: " + extractor.readString(buffer));
      System.out.println();
    }

    System.out.println(UUID.randomUUID().toString().length());
  }

}
