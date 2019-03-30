package hit_counter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TimeProvider whose value can be explicitly set and advanced for testing purposes.  Supports time values in milliseconds, microseconds and nanoseconds.
 * <p>
 * Created by Peter Lawrey on 10/03/16.
 **/
public class SetTimeProvider implements TimeProvider {
  private final AtomicLong nanoTime;
  private long autoIncrement = 0;

  public SetTimeProvider() {
    this(0L);
  }

  public SetTimeProvider(long initialNanos) {
    nanoTime = new AtomicLong(initialNanos);
  }

  public SetTimeProvider(String timestamp) {
    Instant dateTime = LocalDateTime.parse(timestamp).toInstant(ZoneOffset.UTC);
    long initialNanos = dateTime.getEpochSecond() * 1_000_000_000L;
    if (dateTime.isSupported(ChronoField.NANO_OF_SECOND))
      initialNanos += dateTime.getLong(ChronoField.NANO_OF_SECOND);
    nanoTime = new AtomicLong(initialNanos);
  }

  public SetTimeProvider autoIncrement(long autoIncrement, TimeUnit timeUnit) {
    this.autoIncrement = timeUnit.toNanos(autoIncrement);
    return this;
  }

  /**
   * Set the current time in milliseconds.
   *
   * @param millis New time value in milliseconds since the epoch. May not be less than the previous value.
   */
  public void currentTimeMillis(long millis) {
    currentTimeNanos(TimeUnit.MILLISECONDS.toNanos(millis));
  }

  @Override
  public long currentTimeMillis() {
    return TimeUnit.NANOSECONDS.toMillis(currentTimeNanos());
  }

  /**
   * Set the current time in nanoseconds.
   *
   * @param nanos New time value in nanoseconds since the epoch. May not be less than the previous value.
   */
  public void currentTimeNanos(long nanos) {
    if (nanos < nanoTime.get()) throw new IllegalArgumentException("Cannot go back in time!");
    nanoTime.set(nanos);
  }

  public long currentTimeNanos() {
    return nanoTime.getAndAdd(autoIncrement);
  }

  /**
   * Advances time in milliseconds.
   *
   * @param millis duration.
   */
  public SetTimeProvider advanceMillis(long millis) {
    advanceNanos(TimeUnit.MILLISECONDS.toNanos(millis));
    return this;
  }

  /**
   * Advances time in microseconds.
   *
   * @param micros duration.
   */
  public SetTimeProvider advanceMicros(long micros) {
    advanceNanos(TimeUnit.MICROSECONDS.toNanos(micros));
    return this;
  }

  /**
   * Advances time in nanoseconds.
   *
   * @param nanos duration.
   */
  public SetTimeProvider advanceNanos(long nanos) {
    nanoTime.addAndGet(nanos);
    return this;
  }

}
