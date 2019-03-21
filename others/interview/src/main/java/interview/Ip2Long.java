package interview;

import static java.lang.Integer.valueOf;

public final class Ip2Long {
  @SuppressWarnings("WeakerAccess")
  public static long toLong(String ipAddress) {
    return IPAddressV4.from(ipAddress).ip;
  }

  @SuppressWarnings("WeakerAccess")
  public static String fromLong(long n) {
    return IPAddressV4.asString(n);
  }

  public static void main(String[] args) {
    String ip = "192.168.0.1";
    long n = toLong(ip);
    System.out.println("long val: " + n);
    String obtained = fromLong(n);
    if (!ip.equals(obtained)) {
      String err = "Conversion failure; expected: " + ip + ", got: " + obtained;
      throw new AssertionError(err);
    }
  }

  public static final class IPAddressV4 {
    private final long ip;

    private IPAddressV4(int a, int b, int c, int d) {
      validate(a);
      validate(b);
      validate(c);
      validate(d);
      ip = ((long) a << 24) + (b << 16) + (c << 8) + d;
    }

    @Override
    public String toString() {
      return asString(ip);
    }

    static String asString(long ip) {
      int d = (int) (ip % 256);
      int c = (int) ((ip >> 8) % 256);
      int b = (int) ((ip >> 16) % 256);
      int a = (int) (ip >> 24);
      return String.format("%d.%d.%d.%d", a, b, c, d);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      IPAddressV4 that = (IPAddressV4) o;
      return ip == that.ip;
    }

    @Override
    public int hashCode() {
      return Long.hashCode(ip);
    }

    private static void validate(int n) {
      if (n < 0 || n > 255) throw new IllegalArgumentException("Invalid octet: " + n);
    }

    @SuppressWarnings("WeakerAccess")
    public static IPAddressV4 from(String s) {
      String[] xs = s.trim().split("\\.");
      assert xs.length == 4;
      return new IPAddressV4(valueOf(xs[0]), valueOf(xs[1]), valueOf(xs[2]), valueOf(xs[3]));
    }
  }
}
