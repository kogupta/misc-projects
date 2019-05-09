package large_file_test;

import jdk.internal.ref.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Iterator;

import static java.nio.channels.FileChannel.open;
import static java.nio.file.StandardOpenOption.READ;

// https://sourceforge.net/p/kdgcommons/code/HEAD/tree/trunk//src/main/java/net/sf/kdgcommons/buffer/MappedFileBuffer.java
public final class MappedFileBuffer implements Iterator<String>, AutoCloseable {
  private final static int MAX_SEGMENT_SIZE = 1 << 27; // 1 GB, assures alignment

  private final long segmentSize;
  private final MappedByteBuffer[] _buffers;

  private long currIndex;

  public MappedFileBuffer(Path path) throws IOException {
    this.segmentSize = MAX_SEGMENT_SIZE;

    long length = path.toFile().length();
    FileChannel.MapMode mapMode = FileChannel.MapMode.READ_ONLY;
    FileChannel fileChannel = open(path, READ);

    int numBuffers = (int) (length / segmentSize) + ((length % segmentSize != 0) ? 1 : 0);
    _buffers = new MappedByteBuffer[numBuffers];

    int bufIdx = 0;
    for (long offset = 0; offset < length; offset += segmentSize) {
      long remainingFileSize = length - offset;
      long thisSegmentSize = Math.min(2L * segmentSize, remainingFileSize);

      System.out.printf("creating buffer-%d from: %,d to: %,d", bufIdx, offset, thisSegmentSize);
      _buffers[bufIdx++] = fileChannel.map(mapMode, offset, thisSegmentSize);
    }
  }

  private ByteBuffer buffer(long index) {
    ByteBuffer buf = _buffers[(int) (index / segmentSize)];
    buf.position((int) (index % segmentSize));
    return buf;
  }


  @Override
  public void close() throws Exception {
    for (MappedByteBuffer buffer : _buffers) {
      sun.nio.ch.DirectBuffer directBuffer = (DirectBuffer) buffer;
      Cleaner cleaner = directBuffer.cleaner();
      if (cleaner != null) {
        cleaner.clean();
      }
    }


  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public String next() {
    return null;
  }
}
