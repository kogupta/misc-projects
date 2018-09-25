package com.oracle.emcsas.fileSorter;

import com.google.common.flogger.FluentLogger;
import com.oracle.emcsas.fileSorter.datagen.Args.BufSizeConverter;
import com.oracle.emcsas.fileSorter.LmdbStore.ReadRequest;
import com.oracle.emcsas.utils.AppMetrics;
import com.oracle.emcsas.utils.BufferSize;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.BuiltinHelpFormatter;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.util.PathConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.oracle.emcsas.utils.Functions.namedTF;
import static com.oracle.emcsas.utils.Functions.require;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class RWTest {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public static void main(String[] _args) throws IOException {
        Args args = Args.parse(_args);
        start(args);
    }

    public static void start(Args args) throws IOException {
        Path dataDir = args.dataDir;
        int tenantCount = args.tenantCount;

        File f = new File(dataDir.toFile(), "lmdb-index");
        Files.createDirectories(f.toPath());
        LmdbStore store = new LmdbStore(f, tenantCount);

        BlockingQueue<LocalDateTime> readerQ = new LinkedBlockingQueue<>(256);
        BlockingQueue<ReadRequest> deleteQ = new LinkedBlockingQueue<>(256);

        Path input = new File(dataDir.toFile(), "data.bin").toPath();
        Writer writer = new Writer(input, args.size, store, readerQ, deleteQ);
        Reader reader = new Reader(readerQ, deleteQ, store, tenantCount);

        File csvDir = new File(dataDir.toFile(), "csv-metrics");
        AppMetrics metrics = AppMetrics.createStarted(csvDir, 30, SECONDS);
        startThread(reader, "reader");
        startThread(writer, "writer");

        Runtime.getRuntime().addShutdownHook(new Thread(metrics::stop));
    }

    private static void startThread(Runnable r, String name) {
        Thread readerT = new Thread(r);
        readerT.setName(name);
        readerT.setDaemon(false);
        readerT.start();
    }

    public static final class Args {
        final Path dataDir;
        final int tenantCount;
        final BufferSize size;

        private Args(Path dataDir, int tenantCount, BufferSize size) {
            this.dataDir = dataDir;
            this.tenantCount = tenantCount;
            this.size = size;
        }

        public static Args parse(String... args) throws IOException {
            OptionParser parser = new OptionParser();
            parser.allowsUnrecognizedOptions();

            ArgumentAcceptingOptionSpec<Path> dirSpec =
                    parser.accepts("data-dir", "directory to store generated data - provide fully qualified path")
                            .withRequiredArg()
                            .required()
                            .withValuesConvertedBy(new PathConverter());

            ArgumentAcceptingOptionSpec<Integer> tenantSpec =
                    parser.accepts("numTenants", "number of tenants, 0 < n < 100")
                            .withRequiredArg()
                            .ofType(Integer.class)
                            .defaultsTo(10);

            ArgumentAcceptingOptionSpec<BufferSize> bufSizeSpec =
                    parser.acceptsAll(asList("bufferSize", "buffer-size"), "simulated payload size - use one of " + join(", ", BufferSize.allPossibleValues()))
                            .withRequiredArg()
                            .withValuesConvertedBy(new BufSizeConverter())
                            .defaultsTo(BufferSize.OneK);

            parser.acceptsAll(asList("help", "?"), "show help and exit").forHelp();

            parser.printHelpOn(System.out);
            parser.formatHelpWith(new BuiltinHelpFormatter(200, 80));

            OptionSet options = parser.parse(args);
            if (options.has("help")) {
                System.exit(0);
            }

            Path dataDir = options.valueOf(dirSpec);
            Path _created = Files.createDirectories(dataDir);
            logger.atInfo().log("Successfully created directory: %s", _created);

            int tenantCount = options.valueOf(tenantSpec);
            require(tenantCount > 0 && tenantCount < 100, "Tenant count range: 0 < n < 100");

            BufferSize bufferSize = options.valueOf(bufSizeSpec);
            return new Args(dataDir, tenantCount, bufferSize);
        }

        public static Args fromDatagenArgs(com.oracle.emcsas.fileSorter.datagen.Args args) {
            return new Args(args.dataDir, args.tenantCount, args.bufferSize);
        }
    }
}
