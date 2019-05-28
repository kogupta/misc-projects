package binaryJson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule())
      .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  private static final ObjectMapper smileMapper = new ObjectMapper(new SmileFactory())
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule())
      .setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
  private static final ObjectMapper smileArray = smileMapper.copy();

  private static final String parent = "/Users/kgupta/Downloads/spark-data/";

  private static final Path plainTextJsons = Paths.get(parent, "plainText.txt");
  private static final Path smileJsons = Paths.get(parent, "smile.bin");
  private static final Path smileArrayJsons = Paths.get(parent, "smileArray.bin");

  public static void main(String[] args) throws IOException {
    smileArray.configOverride(Pojo.class).setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.ARRAY));

    RandomAccessFile smileyRaf = new RandomAccessFile(smileJsons.toFile(), "rw");
    RandomAccessFile smileyArrayRaf = new RandomAccessFile(smileArrayJsons.toFile(), "rw");

    try (BufferedReader reader = Files.newBufferedReader(plainTextJsons, UTF_8)) {
      String line;
      while ((line = reader.readLine()) != null) {
        Pojo pojo = objectMapper.readValue(line, Pojo.class);

        byte[] smiles = smileMapper.writeValueAsBytes(pojo);
        smileyRaf.writeInt(smiles.length);
        smileyRaf.write(smiles);

        byte[] bytes = smileArray.writeValueAsBytes(pojo);
        smileyArrayRaf.writeInt(bytes.length);
        smileyArrayRaf.write(bytes);
      }
    }
  }

}
