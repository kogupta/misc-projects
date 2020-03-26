package com.conviva.ondemand;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import static com.conviva.ondemand.Utils.*;

public class QueryGenerator {
  private static final String urlTemplate = "curl -v -X POST -H 'Content-Type: application/json' '$ENDPOINT' -d '$PAYLOAD'";

  public static void main(String[] args) throws IOException {
    // TODO: support `--config <config/file/path>`
    // as of now: assume config file path is passed as parameter
//    String cfgPath = args[0];
    String cfgPath = "/Users/kgupta/depot/misc-projects/others/druid-test-client/src/main/resources/config.properties";

    Properties prop = loadProperties(cfgPath);
    boolean isMgw = Boolean.parseBoolean(prop.getProperty("mgw_query"));

    String endpoint = isMgw ? prop.getProperty("mgw_endpoint") : prop.getProperty("druid_endpoint");
    String payload = isMgw ? readResource("mgw_payload.json") : readResource("druid_payload.json");
    String url = urlTemplate.replace("$ENDPOINT", endpoint)
        .replace("$PAYLOAD", payload);

    // interval
    // granularity: PT1M or PT1H
    // metrics

    List<String> intervals = Intervals.dailyQueries();
    String metricCount = isMgw ? "all" : "5"; // change according to Druid template json
    System.out.printf("To execute %d daily queries for %s metrics%n", intervals.size(), metricCount);
    buildAndPrintQueries(url, isMgw, "PT1M", intervals, prop);

//    List<String> weeklyIntervals = Intervals.weeklyQueries();
//    System.out.printf("To execute %d weekly queries for %s metrics%n", weeklyIntervals.size(), metricCount);
//    buildAndPrintQueries(url, isMgw, "PT1H", weeklyIntervals, prop);
//
//    List<String> monthlyIntervals = Intervals.monthlyQueries();
//    System.out.printf("To execute %d monthly queries for %s metrics%n", monthlyIntervals.size(), metricCount);
//    buildAndPrintQueries(url, isMgw, "PT1H", monthlyIntervals, prop);
  }

  private static void buildAndPrintQueries(String url, boolean isMgw, String granularity,
                                           List<String> intervals, Properties prop) {
    String qry = url.replace("$1M_or_1H", granularity);
    if (isMgw) qry = qry.replace("$METRICS", metrics(19));

    String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    String fromProps = prop.getProperty("druid_query_suffix");

    for (String interval : intervals) {
      String cmd = qry.replace("$INTERVAL", interval);
      if (!isMgw) {
        String start = interval.split("/")[0].substring(0, 10);
        String suffix = fromProps + "_" + today + "_" + start;
        cmd = cmd.replace("$SUFFIX", suffix);
      }

      System.out.println(cmd);
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
