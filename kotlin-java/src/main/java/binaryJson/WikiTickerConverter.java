package binaryJson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;
import static com.fasterxml.jackson.annotation.JsonFormat.Value.forShape;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.newBufferedReader;

public final class WikiTickerConverter {
  private static final String parent = "/tmp";
  private static final String input = "wikiticker-2015-09-12-sampled.json";
  private static final String smile = "smile-encoded/smile.bin";
  private static final String smileArray = "smile-encoded/smileArray.bin";

  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule())
      .setSerializationInclusion(JsonInclude.Include.NON_ABSENT);

  private static final ObjectMapper smileMapper = new ObjectMapper(new SmileFactory())
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule())
      .setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
  private static final ObjectMapper smileColMapper = createArrayMapper(smileMapper);

  private static ObjectMapper createArrayMapper(ObjectMapper mapper) {
    ObjectMapper copy = mapper.copy();
    copy.configOverride(WikiTicker.class)
        .setFormat(forShape(ARRAY));
    return copy;
  }

  public static void main(String[] args) throws IOException {
    try (BufferedReader reader = newBufferedReader(Paths.get(parent, input), UTF_8);
         RandomAccessFile smileRaf = new RandomAccessFile(new File(parent, smile), "rw");
         RandomAccessFile smileArrRaf = new RandomAccessFile(new File(parent, smileArray), "rw")
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        WikiTicker pojo = objectMapper.readValue(line, WikiTicker.class);

        smileRaf.write(smileMapper.writeValueAsBytes(pojo));
        smileRaf.writeUTF(lineSeparator());

        smileArrRaf.write(smileColMapper.writeValueAsBytes(pojo));
        smileArrRaf.writeUTF(lineSeparator());
      }
    }
  }
}
