package hit_counter;

@FunctionalInterface
public interface TimeProvider {
  long currentTimeMillis();

  enum SystemTimeProvider implements TimeProvider {
    Instance;

    @Override
    public long currentTimeMillis() {
      return System.currentTimeMillis();
    }
  }
}
