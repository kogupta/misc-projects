package interview.threading;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public final class OrderedOps {
  private static final int countElements = 10;
  private static final int[] elements = toConsume(countElements);
  private static final int numThreads = 3;
  private static final CountDownLatch latch = new CountDownLatch(countElements);

  public static void main(String[] args) {
    ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    for (int element : elements) {
      queue.add(element);
    }


  }

//  private static final class OrderedThread extends Thread {
//    private final int id;
//    private final CountDownLatch latch;
//    private final ConcurrentLinkedQueue<Integer> queue;
//    private volatile boolean run = true;
//
//    OrderedThread(int id, ConcurrentLinkedQueue<Integer> queue, CountDownLatch latch) {
//      super(String.valueOf(validate(id)));
//      this.id = id;
//      this.latch = latch;
//      this.queue = queue;
//    }
//
//    @Override
//    public void run() {
//      while (run && !queue.isEmpty()) {
//        queue.
//      }
//
//
//
//
//
//
//      final int length = elements.length;
//      while (currIndex < length) {
//        synchronized (lock) {
//          while (currIndex % numThreads != id) {
//            try {
//              lock.wait();
//            } catch (InterruptedException e) { e.printStackTrace();}
//          }
//
//          if (currIndex < length) {
//            System.out.printf("Thread:%s -> %d%n", currentThread().getName(), elements[currIndex]);
//            currIndex++;
//            latch.countDown();
//          }
//          lock.notifyAll();
//        }
//      }
//
//      System.out.printf("Thread: %s -> came out of loop", currentThread().getName());
//    }
//
//    private static int validate(int n) {
//      if (n < 0) { throw new IllegalArgumentException("id should be positive!"); }
//      return n;
//    }
//  }

  @SuppressWarnings("SameParameterValue")
  private static int[] toConsume(int countElements) {
    int[] arr = new int[countElements];
    Arrays.setAll(arr, i -> i);
    System.out.println(Arrays.toString(arr));
    return arr;
  }

}
