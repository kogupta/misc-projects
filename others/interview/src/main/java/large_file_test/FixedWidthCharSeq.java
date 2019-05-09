package large_file_test;

public final class FixedWidthCharSeq implements CharSequence {
  private final char[] chars;
  private final int length;

  public FixedWidthCharSeq(int length) {
    assert length > 0;
    this.chars = new char[length];
    this.length = length;
  }

  @Override
  public int length() { return length;}

  @Override
  public char charAt(int index) { return chars[index];}

  public void readFrom(CharSequence s, int from) {
    for (int i = 0; i < length; i++) {
      chars[i] = s.charAt(from + i);
    }
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return null;
  }

  @Override
  public String toString() { return new String(chars);}
}
