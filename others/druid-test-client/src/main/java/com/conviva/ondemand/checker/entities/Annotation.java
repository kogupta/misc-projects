package com.conviva.ondemand.checker.entities;

public class Annotation {
  private final String type;
  private final String cause;
  private final String value;

  public Annotation(String type, String cause, String value) {
    this.type = type;
    this.cause = cause;
    this.value = value;
  }

  public String getType() { return type; }
  public String getCause() { return cause; }
  public String getValue() { return value; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Annotation that = (Annotation) o;

    if (!type.equals(that.type)) return false;
    if (!cause.equals(that.cause)) return false;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + cause.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }
}
