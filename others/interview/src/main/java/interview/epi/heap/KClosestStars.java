package interview.epi.heap;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static interview.epi.array.SortFunctions.assertionStatus;
import static java.util.Comparator.comparingDouble;

public final class KClosestStars {
  public static void main(String[] args) {
    assertionStatus();


  }

  private static List<Star> kClosest(Iterator<Star> stars, int k) {
    Comparator<Star> cmp = comparingDouble(Star::distance).reversed();
    PriorityQueue<Star> maxHeap = new PriorityQueue<>(k, cmp);
    while (stars.hasNext()) {
      maxHeap.add(stars.next());
      if (maxHeap.size() == k + 1) {
        maxHeap.remove();
      }
    }

    List<Star> result = new ArrayList<>(k);
    result.addAll(maxHeap);
    Collections.sort(result);
    return result;
  }

  private static final class Star implements Comparable<Star> {
    private final double x, y, z, distance;

    private Star(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.distance = Math.sqrt(x * x + y * y + z * z);
    }

    private double distance() { return distance;}

    @Override
    public int compareTo(@NotNull Star o) {
      return Double.compare(distance, o.distance);
    }
  }
}
