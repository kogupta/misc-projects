package com.conviva.ondemand;

import com.google.common.io.Resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

public final class Utils {
  private Utils() {}

  public static void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static Properties loadProperties(String cfgPath) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(cfgPath));
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    Properties prop = new Properties();
    prop.load(bais);
    return prop;
  }

  @SuppressWarnings("UnstableApiUsage")
  public static String readResource(String fName) {
    try {
      URL url = Resources.getResource(fName);
      return Resources.toString(url, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static boolean readBooleanProperty(Properties p, String key) {
    return Boolean.parseBoolean(p.getProperty(key));
  }

  public static int[] readIntArrayProperty(Properties p, String key) {
    String s = p.getProperty(key);
    if (s == null || s.trim().isEmpty()) return new int[0];

    String[] xs = s.split(",");
    return Arrays.stream(xs)
        .map(String::trim)
        .mapToInt(Integer::parseInt)
        .toArray();
  }


}
