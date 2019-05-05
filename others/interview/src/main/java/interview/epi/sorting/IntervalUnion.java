package interview.epi.sorting;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.IntBinaryOperator;

import static interview.epi.array.SortFunctions.assertionStatus;
import static interview.epi.sorting.Interval.*;

public final class IntervalUnion {
  public static void main(String[] args) {
    assertionStatus();

    List<Interval> intervals = Arrays.asList(
        closed(2, 4), closedOpen(8, 11), open(13, 15), open(16, 17),
        closed(1, 1), closedOpen(3, 4), closedOpen(7, 8), openClosed(12, 16),
        open(0, 3), closedOpen(5, 7), openClosed(9, 11), closed(12, 14)
    );

    Collections.sort(intervals);
//    display(intervals);

    List<Interval> result = union(intervals);
//    System.out.println(result);

    var expected = List.of(openClosed(0, 4), closed(5, 11), closedOpen(12, 17));
    assert expected.equals(result) :
        "Expected: " + expected + ", got: " + result;
  }

  @NotNull
  private static List<Interval> union(List<Interval> intervals) {
    List<Interval> result = new ArrayList<>();
    result.add(intervals.get(0));
    for (int i = 1; i < intervals.size(); i++) {
      Interval curr = intervals.get(i);
      Interval last = result.get(result.size() - 1);
      if (last.canMergeWith(curr)) {
        last = result.remove(result.size() - 1);
        Interval merged = last.merge(curr);
        result.add(merged);
      } else {
        result.add(curr);
      }
    }
    return result;
  }

  private static void display(List<Interval> intervals) {
    for (List<Interval> xs : Lists.partition(intervals, 4)) {
      StringJoiner joiner = new StringJoiner(", ");
      for (Interval x : xs) {
        joiner.add(x.toString());
      }

      System.out.println(joiner.toString());
    }
  }
}

class EndPoint {
  final int val;
  final boolean isClosed;

  EndPoint(int val, boolean isClosed) {
    this.val = val;
    this.isClosed = isClosed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EndPoint endPoint = (EndPoint) o;
    return val == endPoint.val && isClosed == endPoint.isClosed;
  }

  @Override
  public int hashCode() {
    return 31 * val + (isClosed ? 1 : 0);
  }
}

class Interval implements Comparable<Interval> {
  private static final Comparator<Interval> cmp =
      Comparator.<Interval>comparingInt(i -> i.from.val)
          .thenComparing(i -> !i.from.isClosed) // true, then false
          .thenComparing(i -> i.to.val)
          .thenComparing(i -> i.to.isClosed); // false, then true
  // note: in jdk, false < true

  private final EndPoint from, to;

  private Interval(int from, int to, boolean startClosed, boolean endClosed) {
    assert from <= to;

    this.from = new EndPoint(from, startClosed);
    this.to = new EndPoint(to, endClosed);
  }

  private Interval(EndPoint left, EndPoint right) {
    this.from = left;
    this.to = right;
  }

  @Override
  public int compareTo(@NotNull Interval o) { return cmp.compare(this, o);}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Interval interval = (Interval) o;
    return from.equals(interval.from) && to.equals(interval.to);
  }

  @Override
  public int hashCode() { return 31 * from.hashCode() + to.hashCode();}

  @Override
  public String toString() {
    String start = from.isClosed ? "[" : "(";
    String end = to.isClosed ? "]" : ")";
    return start + from.val + ", " + to.val + end;
  }

  boolean canMergeWith(@NotNull Interval other) {
    assert compareTo(other) <= 0;

    return contains(other.from.val) || isContiguous(other);
  }

  private boolean isContiguous(@NotNull Interval other) {
    assert compareTo(other) <= 0;

    EndPoint next = other.from;
    return (to.val == next.val) && (to.isClosed || next.isClosed);
  }

  Interval merge(@NotNull Interval other) {
    assert canMergeWith(other);

    EndPoint left = ep(from, other.from, Math::min);
    EndPoint right = ep(to, other.to, Math::max);

    return new Interval(left, right);
  }

  private static EndPoint ep(EndPoint a, EndPoint b, IntBinaryOperator minOrMax) {
    int n = minOrMax.applyAsInt(a.val, b.val);
    boolean closed = a.val == b.val ?
        a.isClosed || b.isClosed :
        (n == a.val ? a.isClosed : b.isClosed);
    return new EndPoint(n, closed);
  }

  private boolean contains(int n) {
    if (from.val < n && n < to.val) return true;

    if (n == from.val) return from.isClosed;

    if (n == to.val) return to.isClosed;

    return false;
  }

  static Interval closed(int a, int b) {
    return new Interval(a, b, true, true);
  }

  static Interval closedOpen(int a, int b) {
    return new Interval(a, b, true, false);
  }

  static Interval openClosed(int a, int b) {
    return new Interval(a, b, false, true);
  }

  static Interval open(int a, int b) {
    return new Interval(a, b, false, false);
  }
}