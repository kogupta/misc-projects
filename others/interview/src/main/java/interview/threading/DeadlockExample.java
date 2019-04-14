package interview.threading;

// java specialists example
public class DeadlockExample extends Thread {
  private final Object monitor1, monitor2;

  public DeadlockExample(Object monitor1, Object monitor2) {
    this.monitor1 = monitor1;
    this.monitor2 = monitor2;
  }

  public void run() {
    while (true) {
      synchronized (monitor1) {
        synchronized (monitor2) {
          System.out.println("No deadlock yet ...");
        }
      }
    }
  }

  public static void main(String... args) {
    Object a = new Object();
    Object b = new Object();
    new DeadlockExample(a, b).start();
    new DeadlockExample(b, a).start();
  }
}
