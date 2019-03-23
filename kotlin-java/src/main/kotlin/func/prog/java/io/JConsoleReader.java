package func.prog.java.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class JConsoleReader extends AbstractReader {
  private JConsoleReader() {
    super(init());
  }

  private static BufferedReader init() {
    InputStreamReader in = new InputStreamReader(System.in);
    return new BufferedReader(in);
  }
}
