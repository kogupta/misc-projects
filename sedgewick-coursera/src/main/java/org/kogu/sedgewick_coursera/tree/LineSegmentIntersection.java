package org.kogu.sedgewick_coursera.tree;

import edu.princeton.cs.algs4.MinPQ;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;

import java.util.Comparator;
import java.util.List;

public final class LineSegmentIntersection {
  private enum Event {
    Start, End, // horizontal lines
    Vertical;   // for vertical lines, start/end does not make sense for x co-ordinates
  }

  // (Point, Event) or, Pair<Point, Event>
  private static final class PointEvent {
    final Point point;
    final Event event;

    private PointEvent(Point point, Event event) {
      this.point = point;
      this.event = event;
    }
  }

  private static final class Point {
    final int x;
    final int y;

    private Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return '(' + x + ", " + y + ')';
    }
  }

  private static final class Line {
    final Point from;
    final Point to;

    private Line(Point from, Point to) {
      this.from = from;
      this.to = to;
    }

    boolean isHorizontal() { return from.y == to.y; }
    boolean isVertical() { return from.x == to.x; }
  }

  public static void process(List<Line> lines) {
    // linear scan - on each line segment hit [aka, next element from pq o sorted array]
    // store y co-ordinate in bst
    Comparator<PointEvent> cmp = Comparator.
        <PointEvent>comparingInt(pe -> pe.point.x)
        .thenComparing(pe -> pe.event)
        .thenComparing(pe -> pe.point.y);

    MinPQ<PointEvent> pq = new MinPQ<>(cmp);
    for (Line line : lines) {
      // lines are either vertical or horizontal
      if (line.isVertical()) {
        pq.insert(new PointEvent(line.from, Event.Vertical));
        pq.insert(new PointEvent(line.to, Event.Vertical));
      } else {
        pq.insert(new PointEvent(line.from, Event.Start));
        pq.insert(new PointEvent(line.to, Event.End));
      }
    }

    IntRBTreeSet ys = new IntRBTreeSet();
    while (!pq.isEmpty()) {
      PointEvent pointEvent = pq.delMin();
      switch (pointEvent.event) {
        case Start:
          ys.add(pointEvent.point.y); break;
        case End:
          ys.remove(pointEvent.point.y); break;
        case Vertical:
          int a = pointEvent.point.y;
          PointEvent next = pq.delMin();
          assert next.event == Event.Vertical;
          int b = next.point.y;
          assert a <= b;
          for (int y : ys.subSet(a, b)) {
            Point p = new Point(pointEvent.point.x, y);
            System.out.println("Intersection at : " + p);
          }

          break;
      }
    }

  }
}
