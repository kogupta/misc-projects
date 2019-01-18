package org.kogu.sort_test;

public final class ImmutableRow implements Comparable<ImmutableRow> {
  public final String key;
  public final long value;

  public ImmutableRow(String key, long value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() { return key; }

  public long getValue() { return value; }

  @Override
  public int compareTo(ImmutableRow o) {
    return Long.compare(value, o.value);
  }

  public static ImmutableRow of(String s, long n) {
    return new ImmutableRow(s, n);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ImmutableRow that = (ImmutableRow) o;

    return value == that.value && key.equals(that.key);
  }

  @Override
  public int hashCode() {
    int result = key.hashCode();
    result = 31 * result + (int) (value ^ (value >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "Row[" + key + " -> " + value + "]";
  }
}
