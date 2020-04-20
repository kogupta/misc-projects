package com.conviva.ondemand.queryGen;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class App {
  private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
  private static final OkHttpClient client = new OkHttpClient();
  private static final String url = "http://localhost:8080/v1/metrics/experience_insights/timeseries";

  private static final String ios = "{\"account\": 1960183329, \"metrics\": [$METRICS], \"interval\": \"$INTERVAL\", \"queryGranularity\": \"$1M_or_1H\", \"filter\": {\"type\": \"and\", \"filters\": [{\"type\": \"or\", \"filters\": [{\"type\": \"equal\", \"dimension\": \"GEO_CITY\", \"value\": \"4.229.73.55848\"}, {\"type\": \"equal\", \"dimension\": \"GEO_DMA\", \"value\": \"501\"}, {\"type\": \"equal\", \"dimension\": \"GEO_DMA\", \"value\": \"803\"}]}, {\"type\": \"or\", \"filters\": [{\"type\": \"equal\", \"dimension\": \"m3.dv.os\", \"value\": \"iOS\"}, {\"type\": \"equal\", \"dimension\": \"m3.dv.br\", \"value\": \"Safari\"}]}]}, \"options\": {\"withTotals\": false}}";
  private static final String chrome = "{\"account\": 1960183329, \"metrics\": [$METRICS], \"interval\": \"$INTERVAL\", \"queryGranularity\": \"$1M_or_1H\", \"filter\": {\"type\": \"and\", \"filters\": [{\"type\": \"or\", \"filters\": [{\"type\": \"equal\", \"dimension\": \"GEO_CITY\", \"value\": \"4.229.73.55848\"}, {\"type\": \"equal\", \"dimension\": \"GEO_DMA\", \"value\": \"501\"}, {\"type\": \"equal\", \"dimension\": \"GEO_DMA\", \"value\": \"803\"}]}, {\"type\": \"or\", \"filters\": [{\"type\": \"equal\", \"dimension\": \"m3.dv.os\", \"value\": \"Android\"}, {\"type\": \"equal\", \"dimension\": \"m3.dv.br\", \"value\": \"Chrome\"}]}]}, \"options\": {\"withTotals\": false}}";

  public static void main(String[] args) throws IOException {
    // interval
    // granularity: PT1M or PT1H
    // metrics

    //    int[] metricCount = {2, 4, 8, 19};
    int[] metricCount = {19};
    Multimap<String, Long> daily = ArrayListMultimap.create();
    Multimap<String, Long> weekly = ArrayListMultimap.create();
    Multimap<String, Long> monthly = ArrayListMultimap.create();

    for (int count : metricCount) {
      List<String> intervals = Intervals.dailyQueries();
      System.out.printf("To execute %d daily queries for %d metrics%n", intervals.size(), count);
      executeQueries(daily, count, "PT1M", intervals);
      displayTimings("Daily:", daily);

      List<String> weeklyIntervals = Intervals.weeklyQueries();
      System.out.printf("To execute %d weekly queries for %d metrics%n", weeklyIntervals.size(), count);
      executeQueries(weekly, count, "PT1H", weeklyIntervals);
      displayTimings("Weekly:", weekly);

      List<String> monthlyIntervals = Intervals.monthlyQueries();
      System.out.printf("To execute %d monthly queries for %d metrics%n", monthlyIntervals.size(), count);
      executeQueries(monthly, count, "PT1H", monthlyIntervals);
      displayTimings("Monthly:", monthly);
    }
  }

  private static void displayTimings(String key, Multimap<String, Long> timings) {
    return;
//    System.out.println(key);
//    System.out.println(formatMultimap(timings));
//    System.out.println();
  }

  private static void executeQueries(Multimap<String, Long> timings,
                                     int count,
                                     String granularity,
                                     List<String> intervals) throws IOException {
    String metrics = metrics(count);
    String iosQry = ios.replace("$METRICS", metrics)
        .replace("$1M_or_1H", granularity);
    String chromeQry = chrome.replace("$METRICS", metrics)
        .replace("$1M_or_1H", granularity);

//    String s = "curl --request POST 'http://localhost:8080/v1/metrics/experience_insights/timeseries' -H 'Content-Type: application/json' -d '{\"account\": 1960183329, \"metrics\": [$METRICS], \"interval\": \"$INTERVAL\", \"queryGranularity\": \"$1M_or_1H\", \"filter\": {\"type\": \"and\", \"filters\": [{\"type\": \"not\", \"filter\": {\"type\": \"equal\", \"dimension\": \"GEO_DMA\", \"value\": \"-1\"}}]}, \"options\": {\"withTotals\": false}}'".replace("$METRICS", metrics);

    for (String interval : intervals) {
      iosQry = iosQry.replace("$INTERVAL", interval);
      chromeQry = chromeQry.replace("$INTERVAL", interval);
      long time = timeTsQueries(iosQry, chromeQry);
      timings.put(interval, time);

//      Utils.delay(30_000);

//      String qry = s.replace("$INTERVAL", interval).replace("$1M_or_1H", granularity);
//      System.out.println(qry);
      System.out.println(iosQry);
    }
  }

  private static long timeTsQueries(String iosQry, String chromeQry) throws IOException {
    long t0 = System.nanoTime();
    int ignore1 = executeQuery(iosQry);
    long t1 = System.nanoTime();
//    int ignore2 = executeQuery(chromeQry);

    long chromeRespTime = System.nanoTime() - t1;
    long iosRespTime = t1 - t0;
    return Math.max(chromeRespTime, iosRespTime);
  }

  private static int executeQuery(String json) throws IOException {
//    Utils.delay(1);
//    return 1;

    Request request = new Request.Builder()
        .url(url)
        .post(RequestBody.create(json, JSON))
        .build();

    try (Response response = client.newCall(request).execute()) {
      byte[] bytes = response.body().bytes();
      return bytes.length;
    }
  }

  private static String metrics(int count) {
    if (count == 2) return "\"Attempts\", \"MinutesPlayedPerEndedPlay\"";

    if (count == 4)
      return "\"Attempts\", \"VideoStartFailuresPercentage\", \"ExitBeforeVideoStartsPercentage\", \"PlaysPercentage\"";

    if (count == 8)
      return "\"Attempts\", \"VideoStartFailuresPercentage\", \"ExitBeforeVideoStartsPercentage\", \"Plays\", \"PlaysPercentage\", \"PercentageComplete\", \"ConcurrentPlays\", \"MinutesPlayedPerEndedPlay\"";

    // all
    return "\"Attempts\", \"VideoStartFailuresPercentage\", \"ExitBeforeVideoStartsPercentage\", \"Plays\", \"PlaysPercentage\", \"PercentageComplete\", \"ConcurrentPlays\", \"RebufferingRatio\", \"ConnectionInducedRebufferingRatio\", \"VideoPlaybackFailuresPercentage\", \"VideoStartTime\", \"VideoRestartTime\", \"Bitrate\", \"Framerate\", \"EndedPlays\", \"UniqueDevices\", \"MinutesPlayed\", \"MinutesPlayedPerUniqueDevice\", \"MinutesPlayedPerEndedPlay\"";
  }
}
