package large_file_test;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.nanoTime;
import static java.lang.System.out;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.file.Files.newBufferedReader;
import static java.util.concurrent.TimeUnit.*;

// Problem statement:
//   - download large zip file of text from the Federal Elections Commission,
//   - read that data out of the .txt file supplied, and
//   - supply the following info:
//  * Write a program that will print out the total number of lines in the file.
//  * Notice that the 8th column contains a person’s name. Write a program that loads in this data and creates an array with all name strings. Print out the 432nd and 43243rd names.
//  * Notice that the 5th column contains a form of date. Count how many donations occurred in each month and print out the results.
//  * Notice that the 8th column contains a person’s name. Create an array with each first name. Identify the most common first name in the data and how many times it occurs.
// download link: https://www.fec.gov/files/bulk-downloads/2018/indiv18.zip
// posts: https://stuartmarks.wordpress.com/2019/01/11/processing-large-files-in-java/
public final class Main {
  private static final int[] indices = {0, 432, 43243};
  private static final String parentDir = "C:\\Users\\kohin\\Downloads\\java-large-file-test";
  private static final String[] files = {"10k_rows.txt", "100k_rows.txt", "200k_rows.txt",
      "400k_rows.txt", "800k_rows.txt", "1M_rows.txt", "10M_rows.txt", "itcont.txt"
  };

  public static void main(String[] args) throws IOException {
    // sample row:
    // C00629618|N|TER|P|201701230300133512|15C|IND|PEREZ, JOHN A|LOS ANGELES|CA|90017|PRINCIPAL|DOUBLE NICKEL ADVISORS|01032017|40|H6CA34245|SA01251735122|1141239|||2012520171368850783

    for (String file : files) {
      Path path = Paths.get(parentDir, file);
      out.println("to process: " + path);
      process(path);
      out.println();
    }
  }

  private static void process(Path path) throws IOException {
    long start = nanoTime();

    List<String> names = new ArrayList<>();
    Int2IntOpenHashMap int2intMap = new Int2IntOpenHashMap();

    try (BufferedReader bReader = newBufferedReader(path, US_ASCII)) {
      CharSequence[] dateNamePair = new CharSequence[2];
      dateNamePair[0] = new FixedWidthCharSeq(6);

      String line;
      while ((line = bReader.readLine()) != null) {
        parse(line, dateNamePair);
        int yearMonth = Integer.parseInt(dateNamePair[0], 0, 6, 10);
        int2intMap.merge(yearMonth, 1, Integer::sum);
        names.add(dateNamePair[1].toString());
      }
    }

    displayTime(nanoTime() - start);

    out.printf("# of rows: %,3d%n", names.size());

    for (int index : indices) {
      if (index >= names.size()) continue;

      out.printf("name at index: %d => %s%n", index, names.get(index));
    }

    displayDonations(int2intMap);
  }

  private static void parse(String line, CharSequence[] acc) {
    int col = 0;
    for (int i = 0, start = 0, len = line.length(); i < len && col < 8; i++) {
      char c = line.charAt(i);
      if (c == '|') {
        col++;
        if (col == 8) {
          acc[1] = line.substring(start, i);
        } else if (col == 5) {
          FixedWidthCharSeq seq = (FixedWidthCharSeq) acc[0];
          seq.readFrom(line, start);
        }
        start = i + 1;
      }
    }
  }

  private static void displayDonations(Int2IntOpenHashMap int2intMap) {
    Comparator<Int2IntMap.Entry> cmp = Comparator
        .comparingInt(Int2IntMap.Entry::getIntValue)
        .thenComparingInt(Int2IntMap.Entry::getIntKey)
        .reversed();

    out.println("Monthly donations: ");

    int2intMap.int2IntEntrySet()
        .stream()
        .sorted(cmp)
        .forEach(x -> out.printf("%d => %,9d%n", x.getIntKey(), x.getIntValue()));
  }

  private static void displayTime(long nanos) {
    TimeUnit unit = chooseUnit(nanos);
    double value = (double) nanos / NANOSECONDS.convert(1, unit);
    out.printf("Time taken: %.4g %s%n", value, abbreviate(unit));
  }
  // split a row by '|' and capture 5th and 8th column

  private static TimeUnit chooseUnit(long nanos) {
    if (DAYS.convert(nanos, NANOSECONDS) > 0) {
      return DAYS;
    }
    if (HOURS.convert(nanos, NANOSECONDS) > 0) {
      return HOURS;
    }
    if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
      return MINUTES;
    }
    if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
      return SECONDS;
    }
    if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
      return MILLISECONDS;
    }
    if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
      return MICROSECONDS;
    }
    return NANOSECONDS;
  }

  private static String abbreviate(TimeUnit unit) {
    return switch (unit) {
      case NANOSECONDS -> "ns";
      case MICROSECONDS -> "\u03bcs"; // μs
      case MILLISECONDS -> "ms";
      case SECONDS -> "s";
      case MINUTES -> "min";
      case HOURS -> "h";
      case DAYS -> "d";
    };
  }
}
