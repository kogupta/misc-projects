package interview.threading;

import java.util.concurrent.CountDownLatch;

public final class Deadlock101 {
  private static final CountDownLatch latch = new CountDownLatch(2);

  public static void main(String[] args) throws InterruptedException {
    Lock one = new Lock("one");
    Lock two = new Lock("two");

    Thread a = new Thread(new LockConsumer(one, two, "A"));
    Thread b = new Thread(new LockConsumer(two, one, "B"));
    a.start();
    b.start();

    latch.await();
  }

  private static final class Lock {
    final String name;

    private Lock(String name) {this.name = name;}
  }

  private static final class LockConsumer implements Runnable{
    private final Lock a, b;
    private final String name;

    private LockConsumer(Lock a, Lock b, String name) {
      this.a = a;
      this.b = b;
      this.name = name;
    }

    public void run() {
      synchronized (a) {
        System.out.println(name + " -- [acquired lock] --> " + a.name);
        synchronized (b) {
          latch.countDown();
          System.out.println(name + " -- [acquired lock] --> " + b.name);
          System.out.println("No deadlock yet ...");
        }
      }
    }
  }
}
