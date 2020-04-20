package com.conviva.ondemand.checker.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;

public final class IntervalData {
  private final LocalDateTime timestamp;
  private final Map<Metric, Double> result;

  @JsonCreator
  public IntervalData(@JsonProperty("timestamp") LocalDateTime timestamp,
                      @JsonProperty("result") Map<Metric, Double> result) {
    this.timestamp = timestamp;
    this.result = result;
  }

  public LocalDateTime getTimestamp() { return timestamp; }

  public Map<Metric, Double> getResult() { return result; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    IntervalData that = (IntervalData) o;

    if (!timestamp.equals(that.timestamp)) return false;
    return result.equals(that.result);
  }

  @Override
  public int hashCode() {
    int result1 = timestamp.hashCode();
    result1 = 31 * result1 + result.hashCode();
    return result1;
  }
}
