package interview.threading;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * Poor man's disruptor!
 * <br>
 *   Simulate consumer side of disruptor - multiple consumers added in linear fashion;
 * <p>
 * problem statement:
 *   you are provided n threads, serially execute them.
 *   Thread-1, thread-2, ... so on
 */
public final class OrderedLocking {
  private static final int countElements = 100; // stream size??
  private static final int numThreads = 3; // aka, pool size, # of consumers

  private static final int[] elements = toConsume(countElements);
  private static final CountDownLatch latch = new CountDownLatch(countElements);

  public static void main(String[] args) throws InterruptedException {
    final Object lock = new Object();
    OrderedThread[] threads = new OrderedThread[numThreads];
    Arrays.setAll(threads, idx -> new OrderedThread(idx, lock, elements, latch));

    forEach(threads, Thread::start);

    System.out.println("Waiting on latch ... ");
    latch.await();
  }

  @SuppressWarnings("SameParameterValue")
  private static int[] toConsume(int countElements) {
    int[] arr = new int[countElements];
    Arrays.setAll(arr, i -> i);
    System.out.println(Arrays.toString(arr));
    return arr;
  }

  private static <T> void forEach(T[] array, Consumer<T> action) {
    for (T t : array) action.accept(t);
  }

  private static final class OrderedThread extends Thread {
    private static volatile int currIndex = 0;

    private final int id;
    private final Object lock;
    private final int[] elements;
    private final CountDownLatch latch;

    OrderedThread(int id, Object lock, int[] elements, CountDownLatch latch) {
      super(String.valueOf(validate(id)));
      this.id = id;
      this.lock = lock;
      this.elements = elements;
      this.latch = latch;
    }

    @Override
    public void run() {
      final int length = elements.length;
      while (currIndex < length) {
        synchronized (lock) {
          while (currIndex % numThreads != id) {
            try {
              lock.wait();
            } catch (InterruptedException e) { e.printStackTrace();}
          }

          if (currIndex >= length) {
            currIndex++;      // reqd for others to spin out of wait
            lock.notifyAll(); // notify others and RELINQUISH lock!

            System.out.printf("Thread: %s -> coming out of loop%n", currentThread().getName());
            break;            // duh!!
          } else {
            System.out.printf("Thread:%s -> %d%n", currentThread().getName(), elements[currIndex]);
            currIndex++;
            latch.countDown();
            lock.notifyAll();
          }
        }
      }

      System.out.printf("Thread: %s -> came out of loop%n", currentThread().getName());
    }

    private static int validate(int n) {
      if (n < 0) { throw new IllegalArgumentException("id should be positive!"); }
      return n;
    }
  }
}
