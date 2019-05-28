package binaryJson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeserializationTest {
  private static final ObjectMapper smileMapper = new ObjectMapper(new SmileFactory())
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule())
      .setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
  private static final ObjectMapper smileColMapper = smileMapper.copy();

  private static final String parent = "/Users/kgupta/Downloads/spark-data/";

  private static final Path smileJsons = Paths.get(parent, "smileArray.bin");
  private static final Path smileDeser = Paths.get(parent, "smileDeser.bin");

  public static void main(String[] args) throws IOException {
    smileColMapper.configOverride(Pojo.class).setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.ARRAY));

    RandomAccessFile target = new RandomAccessFile(smileDeser.toFile(), "rw");
    RandomAccessFile smileyRaf = new RandomAccessFile(smileJsons.toFile(), "r");
    long length = smileyRaf.length();
    byte[] bytes = new byte[8 * 1024];
    while (smileyRaf.getFilePointer() < length) {
      int len = smileyRaf.readInt();
      smileyRaf.readFully(bytes, 0, len);
      Pojo pojo = smileColMapper.readValue(bytes, 0, len, Pojo.class);

      byte[] value = smileMapper.writeValueAsBytes(pojo);
      target.writeInt(value.length);
      target.write(value);
    }
  }

}
