package com.conviva.ondemand.checker.entities;

import com.conviva.ondemand.checker.functions.SerDe;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TimeseriesResponse {
  private final long versionId;
  private final List<IntervalData> intervalData;
  private final Map<Metric, Double> intervalTotals;
  private final List<Annotation> annotations;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public TimeseriesResponse(@JsonProperty("versionId") long versionId,
                            @JsonProperty("intervalData") List<IntervalData> intervalData,
                            @JsonProperty("intervalTotals") Map<Metric, Double> intervalTotals,
                            @JsonProperty("annotations") List<Annotation> annotations) {
    this.versionId = versionId;
    this.intervalData = intervalData;
    this.intervalTotals = intervalTotals;
    this.annotations = annotations;
  }

  public long getVersionId() { return versionId; }

  public List<IntervalData> getIntervalData() { return intervalData; }

  public Map<Metric, Double> getIntervalTotals() { return intervalTotals; }

  public List<Annotation> getAnnotations() { return annotations; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TimeseriesResponse that = (TimeseriesResponse) o;

    if (!intervalData.equals(that.intervalData)) return false;
    return intervalTotals.equals(that.intervalTotals);
  }

  @Override
  public int hashCode() {
    int result = intervalData.hashCode();
    result = 31 * result + intervalTotals.hashCode();
    return result;
  }

  public static void main(String[] args) throws IOException {
    String s = "{ \"versionId\": 1587376827917, \"intervalData\": [ { \"timestamp\": \"2020-04-12T00:00:00.000Z\", \"result\": {\"PlaysPercentage\": 100, \"VideoStartFailuresPercentage\": 0, \"Attempts\": 18, \"Plays\": 18, \"ExitBeforeVideoStartsPercentage\": 0 } }, { \"timestamp\": \"2020-04-12T01:00:00.000Z\", \"result\": {\"PlaysPercentage\": 100, \"VideoStartFailuresPercentage\": 0, \"Attempts\": 3, \"Plays\": 3, \"ExitBeforeVideoStartsPercentage\": 0 } } ], \"intervalTotals\": { \"PlaysPercentage\": 100, \"VideoStartFailuresPercentage\": 0, \"Attempts\": 21, \"Plays\": 21, \"ExitBeforeVideoStartsPercentage\": 0 }, \"annotations\": [] }";

    TimeseriesResponse timeseriesResponse = SerDe.timeseriesReader.readValue(s);
    timeseriesResponse.intervalData.forEach(data -> {
      System.out.println(data.getTimestamp());
      System.out.println("  " + data.getResult());
    });

    System.out.println(SerDe.prettyPrint(timeseriesResponse));
  }
}
