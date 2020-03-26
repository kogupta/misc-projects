package binaryJson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public final class JsonMerge {
  private static final FileFilter zippedFileFilter = f -> f.isFile() && f.getName().endsWith("txt.gz");
  private static final String metadataFile = "_METADATA";

  // configs - can be passed as params to `main`
  private static final String parent = "/Users/kgupta/Downloads/spark-data/PT1H_small/out/";
  private static final Path plainTextJsons = Paths.get("/Users/kgupta/Downloads/spark-data/plainText.txt");

  public static void main(String[] args) throws IOException {
    File parentDir = new File(parent);
    File[] customerDirs = parentDir.listFiles(f -> f.isDirectory() && f.getName().startsWith("customerId="));
    assert customerDirs != null;

    for (File customerDir : customerDirs) {
      System.out.println("Processing directory: " + customerDir.getName());

      List<String> jsons = parseFiles(customerDir);
      appendJsons(jsons);
    }
  }

  private static void appendJsons(List<String> jsons) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(plainTextJsons, UTF_8, CREATE, APPEND)) {
      for (String json : jsons) {
        writer.write(json);
        writer.newLine();
      }
    }
  }

  private static List<String> parseFiles(File customerDir) throws IOException {
    int count = numberOfRows(customerDir.toPath());
    if (count == 0) return Collections.emptyList();

    File[] zippedFiles = customerDir.listFiles(zippedFileFilter);
    if (zippedFiles == null) return Collections.emptyList();

    List<String> acc = new ArrayList<>(count);
    for (File zippedFile : zippedFiles) {
      System.out.println("  parsing: " + zippedFile);

      unzip(zippedFile, acc);
    }

    if (acc.size() != count) throw new AssertionError("Expected: " + count + ", got: " + acc.size());

    return acc;
  }

  private static int numberOfRows(Path directory) throws IOException {
    Path metaFile = directory.resolve(metadataFile);
    String row = Files.readAllLines(metaFile, US_ASCII).get(0);
//    {"numRows":225}%
    String startKey = "{\"numRows\":";
    int startIdx = row.indexOf(startKey) + startKey.length();
    int endIdx = row.lastIndexOf("}");
    return Integer.parseInt(row.substring(startIdx, endIdx));
  }

  private static void unzip(File f, List<String> acc) throws IOException {
    String line;
    try (InputStream fis = new FileInputStream(f);
         GZIPInputStream gis = new GZIPInputStream(fis);
         Reader reader = new InputStreamReader(gis);
         BufferedReader bReader = new BufferedReader(reader)) {
      while ((line = bReader.readLine()) != null)
        acc.add(line);
    }
  }
}
