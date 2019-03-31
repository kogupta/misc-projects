package interview.epi.stack_queue;

import java.util.*;

import static com.google.common.base.Strings.padEnd;
import static com.google.common.base.Strings.repeat;
import static interview.epi.array.SortFunctions.swap;
import static java.lang.System.out;

public final class SunsetView {
  private static final Random r = new Random(31012010);
  private static final int minHeight = 10;
  private static final int maxHeight = 30;

  public static void main(String[] args) {
    _assertionStatus();

    Building[] buildings = generateBuildings();
    List<Building> west2East = west_to_east(buildings);
    System.out.println("west -> east: buildings with view: " + west2East);

    reverse(buildings);
    List<Building> east2West = east_to_west(buildings);
    System.out.println("east -> west: buildings with view: " + east2West);

    assert west2East.equals(east2West);
  }

  private static List<Building> east_to_west(Building[] buildings) {
    Deque<Building> withSunset = new ArrayDeque<>();
    for (Building building : buildings) {
      int currHeight = building.height;
      while (!withSunset.isEmpty() && currHeight >= withSunset.peek().height) {
        withSunset.pop();
      }
      withSunset.push(building);
    }

    List<Building> xs = new ArrayList<>(withSunset);
    return xs;
  }

  private static List<Building> west_to_east(Building[] buildings) {
    // top is WEST
    // buildings are iterated from WEST -> EAST
    int maxHeight = Integer.MIN_VALUE;
    List<Building> result = new ArrayList<>();

    for (Building building : buildings) {
      if (building.height > maxHeight) {
        maxHeight = building.height;
        result.add(building);
      }
    }

    return result;
  }

  private static Building[] generateBuildings() {
    Building[] xs = new Building[10];
    int bound = maxHeight - minHeight;
    Arrays.setAll(xs, i -> Building.with(i, minHeight + r.nextInt(bound)));
    System.out.println(Arrays.toString(xs));
    display(xs);
    return xs;
  }

  private static void display(Building[] buildings) {
    for (Building building : buildings) {
      String xs = repeat("*", building.height);
      String s = padEnd(xs, maxHeight, ' ');
      System.out.println(s + "  (" + building + ")");
    }
  }

  private static final class Building {
    final int id;
    final int height;

    private Building(int id, int height) {
      this.id = id;
      this.height = height;
    }

    @Override
    public String toString() {
      return id + ": " + height;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Building building = (Building) o;

      return id == building.id && height == building.height;
    }

    @Override
    public int hashCode() {
      int result = id;
      result = 31 * result + height;
      return result;
    }

    public static Building with(int id, int height) {
      return new Building(id, height);
    }
  }

  private static <T> void reverse(T[] array) {
    for (int i = 0; i < array.length / 2; i++) {
      int other = array.length - 1 - i;
      swap(array, i, other);
    }
  }


  //<editor-fold desc="assertion status">
  private static void _assertionStatus() {
    String status = SunsetView.class.desiredAssertionStatus() ? "enabled" : "disabled";
    out.println("Assertion: " + status);
    out.println("====================");
  }
  //</editor-fold>
}
