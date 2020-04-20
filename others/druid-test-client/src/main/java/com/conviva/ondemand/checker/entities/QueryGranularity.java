package com.conviva.ondemand.checker.entities;

import java.util.Collections;
import java.util.List;

import static com.conviva.ondemand.checker.functions.Intervals.*;
import static java.util.Arrays.asList;

public enum QueryGranularity {
  P1D {
    @Override
    public List<Interval> intervals() {
      return asList(last7Days(), last30Days());
    }
  },
  PT1H {
    @Override
    public List<Interval> intervals() {
      return asList(yesterday(), dayBeforeYesterday());
    }
  },
  PT1M {
    @Override
    public List<Interval> intervals() {
      return Collections.emptyList();
    }
  };

  public abstract List<Interval> intervals();
}
