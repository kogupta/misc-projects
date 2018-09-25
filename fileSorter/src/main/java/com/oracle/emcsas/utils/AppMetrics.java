package com.oracle.emcsas.utils;

import com.codahale.metrics.*;
import com.codahale.metrics.MetricRegistry.MetricSupplier;
import com.codahale.metrics.jmx.JmxReporter;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class AppMetrics {
    public static final MetricRegistry registry = new MetricRegistry();

    private final ConsoleReporter reporter;
    private final JmxReporter jmxReporter;
    private final CsvReporter csvReporter;

    private final long period;
    private final TimeUnit unit;
    private static final MetricSupplier<Histogram> supplier =
            () -> new Histogram(new SlidingTimeWindowArrayReservoir(30, SECONDS));

    private AppMetrics(File dir, long period, TimeUnit unit) {
        this.period = period;
        this.unit = unit;

        reporter = ConsoleReporter.forRegistry(registry)
                .convertDurationsTo(MILLISECONDS)
                .convertRatesTo(SECONDS)
                .build();
        jmxReporter = JmxReporter.forRegistry(registry)
                .convertDurationsTo(MILLISECONDS)
                .convertRatesTo(SECONDS)
                .build();

        csvReporter = CsvReporter.forRegistry(registry)
                .convertDurationsTo(MILLISECONDS)
                .convertRatesTo(SECONDS)
                .formatFor(Locale.US)
                .build(dir);
    }

    private void start() {
        reporter.start(period, unit);
        jmxReporter.start();
        csvReporter.start(period, unit);
    }

    public void report() {
        reporter.report();
        csvReporter.report();
    }

    public void stop() {
        reporter.stop();
        csvReporter.stop();
        jmxReporter.stop();
    }

    public static Histogram newHistogram(String name) {
        return registry.histogram(name, supplier);
    }

    private static AppMetrics instance;

    public static synchronized AppMetrics createStarted(File dir, long period, TimeUnit unit) {
        if (instance == null) {
            instance = new AppMetrics(dir, period, unit);
            instance.start();
        }
        return instance;
    }
}
