package com.conviva.ondemand;

import com.google.common.collect.Multimap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.stream.Collectors.joining;

public class Intervals {
  public static void main(String[] args) {
    dailyQueries();
    weeklyQueries();
    monthlyQueries();
  }

  public static List<String> dailyQueries() {
    LocalTime midnight = LocalTime.MIDNIGHT;
    LocalDate today = LocalDate.now(ZoneId.of("UTC"));
    LocalDateTime from = LocalDateTime.of(today, midnight);
    LocalDateTime to = from.minusDays(10);
    List<String> xs = new ArrayList<>();
    for (LocalDateTime currDate = from; currDate.isAfter(to); currDate = currDate.minusDays(1)) {
      // 2020-03-01T00:00:00/2020-03-02T00:00:00
      LocalDateTime prevDay = currDate.minusDays(1);
      String interval = prevDay.format(ISO_DATE_TIME) + "/" + currDate.format(ISO_DATE_TIME);
      xs.add(interval);
    }

//    System.out.println(String.join("\n", xs));
    return xs;
  }

  public static List<String> weeklyQueries() {
    LocalTime midnight = LocalTime.MIDNIGHT;
    LocalDate today = LocalDate.now(ZoneId.of("UTC"));
    LocalDateTime from = LocalDateTime.of(today, midnight);
    LocalDateTime to = from.minusWeeks(10);
    List<String> xs = new ArrayList<>();
    for (LocalDateTime currDate = from; currDate.isAfter(to); currDate = currDate.minusWeeks(1)) {
      // 2020-03-01T00:00:00/2020-03-02T00:00:00
      LocalDateTime prevDay = currDate.minusWeeks(1);
      String interval = prevDay.format(ISO_DATE_TIME) + "/" + currDate.format(ISO_DATE_TIME);
      xs.add(interval);
    }

//    System.out.println(String.join("\n", xs));
    return xs;
  }

  public static List<String> monthlyQueries() {
    LocalTime midnight = LocalTime.MIDNIGHT;
    LocalDate today = LocalDate.now(ZoneId.of("UTC"));
    LocalDateTime from = LocalDateTime.of(today, midnight);
    LocalDateTime to = from.minusMonths(3);
    List<String> xs = new ArrayList<>();
    for (LocalDateTime currDate = from; currDate.isAfter(to); currDate = currDate.minusDays(15)) {
      // 2020-03-01T00:00:00/2020-03-02T00:00:00
      LocalDateTime prevDay = currDate.minusMonths(1);
      String interval = prevDay.format(ISO_DATE_TIME) + "/" + currDate.format(ISO_DATE_TIME);
      xs.add(interval);
    }

//    System.out.println(String.join("\n", xs));
    return xs;
  }

  public static String formatMultimap(Multimap<String, Long> xs) {
    StringBuilder sb = new StringBuilder();
    List<Integer> timings = new ArrayList<>(xs.entries().size());
    for (Map.Entry<String, Collection<Long>> xss : xs.asMap().entrySet()) {
      String interval = xss.getKey();
      String values = xss.getValue()
          .stream()
          .mapToInt(aLong -> (int) (aLong / 1_000_000))
          .mapToObj(n -> String.valueOf(n))
          .collect(joining(", ", "[", "]"));
      sb.append(interval).append(':').append(values).append(lineSeparator());

      List<Integer> collect = xss.getValue()
          .stream()
          .mapToInt(aLong -> (int) (aLong / 1_000_000))
          .boxed()
          .collect(Collectors.toList());

      timings.addAll(collect);
    }

    String allTimings = timings.stream()
        .sorted()
        .map(n -> String.valueOf(n))
        .collect(joining(", ", "[", "]"));
    sb.append(allTimings);

    return sb.toString();
  }
}
