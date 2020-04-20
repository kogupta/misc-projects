package com.conviva.ondemand.checker.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeseriesQuery {
  private final int account;
  private final List<Metric> metrics;
  private final Interval interval;
  private final QueryGranularity queryGranularity;
  private final Filter filter;
  private final Map<String, Boolean> options;

  public TimeseriesQuery(int account, List<Metric> metrics, Interval interval, QueryGranularity queryGranularity, Filter filter) {
    this.account = account;
    this.metrics = metrics;
    this.interval = interval;
    this.queryGranularity = queryGranularity;
    this.filter = filter;
    this.options = new HashMap<>();
    options.put("withTotals", true);
  }

  public int getAccount() { return account; }

  public List<Metric> getMetrics() { return metrics; }

  public Interval getInterval() { return interval; }

  public QueryGranularity getQueryGranularity() { return queryGranularity; }

  public Filter getFilter() { return filter; }

  public Map<String, Boolean> getOptions() { return options; }

  public TimeseriesQuery with(Interval other) {
    return new TimeseriesQuery(account, metrics, other, queryGranularity, filter);
  }

  public TimeseriesQuery toIstRequest() {
    LocalDateTime from = interval.getFrom().plusMinutes(330);
    LocalDateTime to = interval.getTo().plusMinutes(330);
    return with(new Interval(from, to));
  }
}
