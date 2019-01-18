package org.kogu.sort_test;

public final class MutableRow implements Comparable<MutableRow> {
  private String key;
  private long value;

  public String getKey() { return key; }

  public void setKey(String key) { this.key = key; }

  public long getValue() { return value; }

  public void setValue(long value) { this.value = value; }

  @Override
  public int compareTo(MutableRow o) {
    return Long.compare(value, o.value);
  }

  @Override
  public String toString() {
    return "Row[" + key + " -> " + value + "]";
  }
}
