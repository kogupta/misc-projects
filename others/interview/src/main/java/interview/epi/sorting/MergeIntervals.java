package interview.epi.sorting;

import java.util.ArrayList;
import java.util.List;

import static interview.epi.array.SortFunctions.assertionStatus;

public final class MergeIntervals {
  public static void main(String[] args) {
    assertionStatus();
    List<Interval> intervals = List.of(
        Interval.between(-4, -1),
        Interval.between(0, 2),
        Interval.between(3, 6),
        Interval.between(7, 9),
        Interval.between(11, 12),
        Interval.between(14, 17)
    );

    List<Interval> merged = merge(intervals, Interval.between(1, 8));
    System.out.println("Obtained: " + merged);
    List<Interval> expected = List.of(
        Interval.between(-4, -1),
        Interval.between(0, 9),
        Interval.between(11, 12),
        Interval.between(14, 17)
    );

    assert expected.equals(merged);
  }

  private static List<Interval> merge(List<Interval> intervals, Interval interval) {
    if (intervals.isEmpty()) return List.of(interval);

    List<Interval> result = new ArrayList<>();
    int i = 0;

    for (; i < intervals.size() && intervals.get(i).to < interval.from; i++) {
      result.add(intervals.get(i));
    }

    Interval merged = null;
    for (; i < intervals.size() && interval.intersect(intervals.get(i)); i++) {
      Interval a = intervals.get(i);
      merged = merged == null ?
          Interval.merge(a, interval) :
          Interval.merge(a, merged);
    }
    if (merged != null) result.add(merged);

    for (; i < intervals.size(); i++) {
      result.add(intervals.get(i));
    }

    return result;
  }

  private static final class Interval {
    final int from, to;

    private Interval(int from, int to) {
      this.from = from;
      this.to = to;
    }

    boolean contains(int n) { return from <= n && n <= to;}

    boolean intersect(Interval other) {
      return contains(other.from) || other.contains(from);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Interval interval = (Interval) o;
      return from == interval.from && to == interval.to;
    }

    @Override
    public int hashCode() {
      return 31 * from + to;
    }

    @Override
    public String toString() {
      return "[" + from + ", " + to + "]";
    }

    static Interval between(int from, int to) {
      assert from < to;
      return new Interval(from, to);
    }

    static Interval merge(Interval a, Interval b) {
      assert a.intersect(b);

      int from = Math.min(a.from, b.from);
      int to = Math.max(a.to, b.to);
      return new Interval(from, to);
    }
  }
}
