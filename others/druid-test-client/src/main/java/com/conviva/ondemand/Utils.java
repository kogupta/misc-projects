package com.conviva.ondemand;

public final class Utils {
  private Utils() {}

  public static void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
