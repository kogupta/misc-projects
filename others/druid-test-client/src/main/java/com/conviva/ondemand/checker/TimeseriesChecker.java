package com.conviva.ondemand.checker;

import com.conviva.ondemand.checker.entities.*;
import com.conviva.ondemand.checker.functions.HttpHelper;
import com.conviva.ondemand.checker.functions.Intervals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.conviva.ondemand.checker.entities.Metric.*;
import static com.conviva.ondemand.checker.entities.QueryGranularity.PT1H;
import static com.conviva.ondemand.checker.functions.SerDe.prettyPrint;
import static java.lang.System.lineSeparator;

public class TimeseriesChecker {
  private static final String prodMgw = "http://instant-filter-5.us-east-1.prod.conviva.com:8080";
  private static final String local = "http://localhost:8080";
  private static final String eiSuffix = "/v1/metrics/experience_insights/timeseries";
  private static final String aeSuffix = "/v1/metrics/ad_experience/timeseries";

  public static void main(String[] args) throws IOException {
    // assume first param is test mgw
    String testMgw = args.length == 0 ? local : args[0];

    Filter a = Filter.Or.of(new Filter.Equal("GEO_CITY", "4.229.73.55848"));
    Filter b = Filter.Or.of(new Filter.Equal("m3.dv.os", "Android"));
    Filter c = Filter.Or.of(new Filter.Equal("CUSTOMER_ID", "1960180565"));
    Filter f = Filter.And.of(a, b, c);
    List<Metric> metrics = Arrays.asList(Attempts, VideoStartFailuresPercentage, ExitBeforeVideoStartsPercentage, Plays, PlaysPercentage);
    TimeseriesQuery request = new TimeseriesQuery(1960183329, metrics, Intervals.lastNHours(1), PT1H, f);

    TimeseriesResponse prod = HttpHelper.timeseries(request, prodMgw + eiSuffix);
    // test is running in IST - no timezone shenanigans
    TimeseriesQuery istRequest = request.toIstRequest();
    TimeseriesResponse test = HttpHelper.timeseries(istRequest, testMgw + eiSuffix);

    if (!prod.equals(test)) {
      String sb = "NOO!!" + lineSeparator() +
          "+++ prod +++" + lineSeparator() +
          prettyPrint(prod) + lineSeparator() +
          "-- test --" + lineSeparator() +
          prettyPrint(test);
      System.out.println(sb);
      System.out.println();
    }
  }
}
