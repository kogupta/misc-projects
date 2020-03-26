package maths2prog;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.*;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public interface Utils {
  static void displayTime(long nanos) {
    TimeUnit unit = chooseUnit(nanos);
    double value = (double) nanos / NANOSECONDS.convert(1, unit);
    out.printf("Time taken: %.4g %s%n", value, abbreviate(unit));
  }

  static TimeUnit chooseUnit(long nanos) {
    if (DAYS.convert(nanos, NANOSECONDS) > 0) {
      return DAYS;
    }
    if (HOURS.convert(nanos, NANOSECONDS) > 0) {
      return HOURS;
    }
    if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
      return MINUTES;
    }
    if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
      return SECONDS;
    }
    if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
      return MILLISECONDS;
    }
    if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
      return MICROSECONDS;
    }
    return NANOSECONDS;
  }

  static String abbreviate(TimeUnit unit) {
    switch (unit) {
      case NANOSECONDS:
        return "ns";
      case MICROSECONDS:
        return "\u03bcs"; // μs
      case MILLISECONDS:
        return "ms";
      case SECONDS:
        return "s";
      case MINUTES:
        return "min";
      case HOURS:
        return "h";
      case DAYS:
        return "d";
      default:
        throw new AssertionError();
    }
  }

  static <T> T time(Supplier<T> block) {
    long t0 = System.nanoTime();
    T result = block.get();
    long t1 = System.nanoTime();
    Utils.displayTime(t1 - t0);
    return result;
  }

}
