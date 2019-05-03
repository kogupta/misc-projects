package interview.epi.sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import static interview.epi.array.SortFunctions.assertionStatus;
import static java.lang.Math.max;

public final class RenderCalendar {
  public static void main(String[] args) {
    assertionStatus();
    Event[] events = {
        Event.between(1, 5),
        Event.between(6, 10),
        Event.between(11, 13),
        Event.between(14, 15),
        Event.between(2, 7),
        Event.between(8, 9),
        Event.between(12, 15),
        Event.between(3, 4),
        Event.between(9, 17)
    };

    EndPoint[] endPoints = toSortedEndPoints(events);
    int maxCount = maxSimultaneousEvents(endPoints);

    assert maxCount == 3;
  }

  private static int maxSimultaneousEvents(EndPoint[] sortedEndPoints) {
    int count = 0, maxCount = 0;
    for (EndPoint endPoint : sortedEndPoints) {
      if (endPoint.isEnd) count--;
      else {
        count++;
        maxCount = max(count, maxCount);
      }

      System.out.println(endPoint + " -> " + count);
    }
    return maxCount;
  }

  @NotNull
  private static EndPoint[] toSortedEndPoints(Event[] events) {
    EndPoint[] endPoints = new EndPoint[events.length * 2];
    for (int i = 0; i < events.length; i++) {
      Event event = events[i];
      EndPoint e1 = new EndPoint(event.start, false);
      EndPoint e2 = new EndPoint(event.end, true);
      endPoints[2 * i] = e1;
      endPoints[2 * i + 1] = e2;
    }
    Arrays.sort(endPoints);
    return endPoints;
  }

  private static final class Event {
    final int start, end;

    private Event(int start, int end) {
      this.start = start;
      this.end = end;
    }

    static Event between(int from, int to) {
      assert from > 0 && to > 0 && from < to;
      return new Event(from, to);
    }
  }

  private static final class EndPoint implements Comparable<EndPoint> {
    private static final Comparator<EndPoint> cmp =
        Comparator.comparingInt((EndPoint e) -> e.time)
            .thenComparing(e -> e.isEnd);

    final int time;
    final boolean isEnd;

    private EndPoint(int time, boolean isEnd) {
      this.time = time;
      this.isEnd = isEnd;
    }

    @Override
    public int compareTo(@NotNull EndPoint o) {
      return cmp.compare(this, o);
    }

    @Override
    public String toString() {
      String s = isEnd ? "end" : "start";
      return String.format("[%d, %s]", time, s);
    }

    static Stream<EndPoint> from(Event event) {
      EndPoint e1 = new EndPoint(event.start, false);
      EndPoint e2 = new EndPoint(event.end, true);
      return Stream.of(e1, e2);
    }
  }
}
