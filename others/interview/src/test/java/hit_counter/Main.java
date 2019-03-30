package hit_counter;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.ZoneOffset.UTC;
import static java.util.stream.Collectors.joining;

public final class Main {
  private static final int durationSeconds = 10;
  private static final int stepMillis = 400;
  private static final LocalDateTime start = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);


  public static void main(String[] args) {
    List<IntEvent> events = generate();
    displayEvents(events);
  }

  private static void displayEvents(List<IntEvent> events) {
    long offset = start.toInstant(UTC).toEpochMilli();

    Lists.partition(events, 10)
        .forEach(x ->
            {
              String s = x.stream()
                  .map(p -> p.toStringFromOffset(offset))
                  .collect(joining(","));
              System.out.println(s);
            }
        );
  }

  private static List<IntEvent> generate() {
    long from = start.toInstant(UTC).toEpochMilli();
    long totalTime = Duration.ofSeconds(durationSeconds).toMillis();
    long to = from + totalTime;

    List<IntEvent> xs = new ArrayList<>();
    int n = 0;
    for (long t = from; t < to; t += stepMillis) {
      IntEvent event = new IntEvent(n++, t);
      xs.add(event);
    }

    return xs;
  }

  private static final class HitCounter {
    private final ArrayList<Integer> buffer;
    private final TimeProvider timeProvider;
    int last5Secs, lastSec;

    private HitCounter(TimeProvider timeProvider) {
      this.timeProvider = timeProvider;
      // milli second resolution
      // counter for each event in last 1 second
      buffer = new ArrayList<>(1_000);
    }

    public void hit(IntEvent event) {
      long now = timeProvider.currentTimeMillis();
      long oldest = now - 1_000; // 1 second
      if (event.timestamp < oldest) {
        return; // too old!
      }

      LocalDateTime time = IntEvent.toDateTime(event.timestamp);
      int millis = (int) time.getLong(ChronoField.MILLI_OF_SECOND);
      int existing = buffer.get(millis);
      buffer.add(millis, existing + millis);
    }

    public int countLast5s() {
      return 0;
    }

    public int countLast10s() {
      return 0;
    }
  }

  private static final class IntEvent {
    final int data;
    final long timestamp;

    public IntEvent(int data) {
      this.data = data;
      timestamp = Instant.now().toEpochMilli();
    }

    public IntEvent(int data, long timestamp) {
      this.data = data;
      this.timestamp = timestamp;
    }

    @Override
    public String toString() {
      LocalDateTime time = toDateTime(timestamp);
      return String.format("[%d, %s]", data, time);
    }

    @NotNull
    public static LocalDateTime toDateTime(long timestamp) {
      long epochSecond = timestamp / 1000;
      long rem = timestamp - epochSecond * 1000;
      int nanos = (int) TimeUnit.MILLISECONDS.toNanos(rem);
      return LocalDateTime.ofEpochSecond(epochSecond, nanos, UTC);
    }

    public String toStringFromOffset(long offset) {
      long n = timestamp - offset;
      return String.format("[%d, %d]", data, n);
    }


  }
}
