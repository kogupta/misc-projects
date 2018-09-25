package com.oracle.emcsas.fileSorter.datagen;

import com.google.common.flogger.FluentLogger;
import com.oracle.emcsas.utils.BufferSize;
import joptsimple.*;
import joptsimple.util.EnumConverter;
import joptsimple.util.PathConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.oracle.emcsas.utils.Functions.require;
import static com.oracle.emcsas.utils.Functions.tryParse;
import static java.lang.String.join;
import static java.util.Arrays.asList;

public final class Args {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public final LocalDateTime fromDate;
    public final LocalDateTime endDate;
    public final Path dataDir;
    public final int tenantCount;
    public final BufferSize bufferSize;

    public Args(LocalDateTime fromDate,
                 LocalDateTime endDate,
                 Path dataDir,
                 int tenantCount,
                 BufferSize bufferSize) {
        this.fromDate = fromDate;
        this.endDate = endDate;
        this.dataDir = dataDir;
        this.tenantCount = tenantCount;
        this.bufferSize = bufferSize;
    }

    public static Args parse(String... args) throws IOException {
        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        List<String> startDateKey = asList("from-date", "fromDate", "startDate", "start-date");
        List<String> endDateKey = asList("to-date", "toDate", "endDate", "end-date");
        ArgumentAcceptingOptionSpec<LocalDateTime> fromDateSpec =
                parser.acceptsAll(startDateKey, "starting date of data generator in YYYY-MM-dd format")
                        .withRequiredArg()
                        .defaultsTo("2018-01-01")
                        .withValuesConvertedBy(new DateConverter());
        ArgumentAcceptingOptionSpec<LocalDateTime> endDateSpec =
                parser.acceptsAll(endDateKey, "end date of data generator in YYYY-MM-dd format")
                        .requiredIf(fromDateSpec)
                        .withRequiredArg()
                        .defaultsTo("2018-02-01")
                        .withValuesConvertedBy(new DateConverter());
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

        LocalDateTime fromDate = options.valueOf(fromDateSpec);
        LocalDateTime endDate = options.valueOf(endDateSpec);
        require(fromDate.isBefore(endDate), "${fromDate} should be before ${endDate}");

        Path dataDir = options.valueOf(dirSpec);
        logger.atInfo().log("Creating directory: %s", dataDir);
        Path _created = Files.createDirectories(dataDir);
        logger.atInfo().log("Successfully created directory: %s", _created);

        int tenantCount = options.valueOf(tenantSpec);
        require(tenantCount > 0 && tenantCount < 100, "Tenant count range: 0 < n < 100");

        BufferSize bufferSize = options.valueOf(bufSizeSpec);
        return new Args(fromDate, endDate, dataDir, tenantCount, bufferSize);
    }

    private static final class DateConverter implements ValueConverter<LocalDateTime> {
        private static final String datePattern = "YYYY-MM-dd"; // ISO_LOCAL_DATE

        @Override
        public LocalDateTime convert(String value) {
            return LocalDate.parse(value).atStartOfDay();
        }

        @Override
        public Class<? extends LocalDateTime> valueType() {
            return LocalDateTime.class;
        }

        @Override
        public String valuePattern() {
            return datePattern;
        }
    }

    public static final class BufSizeConverter extends EnumConverter<BufferSize> {
        public BufSizeConverter() {
            super(BufferSize.class);
        }

        @Override
        public BufferSize convert(String value) {
            return tryParse(value)
                    .flatMap(BufferSize::fromSize)
                    .orElseGet(() -> BufferSize.fromSynonym(value)
                    .orElseGet(() -> super.convert(value)));
        }

        @Override
        public String valuePattern() {
            return join(", ", BufferSize.allPossibleValues());
        }
    }

    public static void main(String[] args) throws IOException {
        Args _args = Args.parse(args);
        System.out.println("From date: " + _args.fromDate.toString());
        System.out.println("End date: " + _args.endDate);
        System.out.println("Storing data at: " + _args.dataDir);
    }
}
