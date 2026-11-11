package e9;

// ALBANES, Luciano Joaquín

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

// ConcurrentHashMap allows concurrent access to the metric maps.
// LongAdder is useful for counters that receive many concurrent updates.
// This avoids synchronizing the whole collector with synchronized methods.

public class MetricsCollectorExample {
    public static void main(String[] args) throws InterruptedException {
        MetricsCollector metrics = MetricsCollector.getInstance();

        // Simulate several components registering metrics at the same time.
        Thread[] workers = new Thread[4];

        for (int t = 0; t < workers.length; t++) {
            workers[t] = new Thread(() -> {
                for (int i = 0; i < 25_000; i++) {
                    metrics.increment("http.requests");
                    metrics.recordLatency("http.request", 10 + (i % 20));

                    if (i % 1_000 == 0) {
                        metrics.increment("http.errors");
                    }
                }
            });

            workers[t].start();
        }

        // Wait for all threads before exporting the final result.
        for (Thread worker : workers) {
            worker.join();
        }

        System.out.println(metrics.export());
    }
}

class MetricsCollector {
    // Singleton: class initialization in Java is thread-safe.
    private static final MetricsCollector INSTANCE = new MetricsCollector();

    // Maximum number of different metrics stored by the collector.
    // This prevents unbounded memory growth if arbitrary metric names are used.
    private static final int MAX_METRICS = 1_000;

    private final ConcurrentHashMap<String, LongAdder> counters =
            new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, LatencyMetric> latencies =
            new ConcurrentHashMap<>();

    // Counts registrations that could not be stored because capacity was full.
    private final LongAdder droppedMetrics = new LongAdder();

    private MetricsCollector() {
    }

    public static MetricsCollector getInstance() {
        return INSTANCE;
    }

    // Registers one occurrence of a counter metric.
    public void increment(String metricName) {
        validateMetricName(metricName);

        LongAdder counter = counters.get(metricName);

        if (counter == null) {
            counter = createCounter(metricName);
        }

        if (counter != null) {
            counter.increment();
        }
    }

    // Registers one latency measurement without keeping every individual value
    // in memory. Only count and accumulated time are stored.
    public void recordLatency(String metricName, long milliseconds) {
        validateMetricName(metricName);

        if (milliseconds < 0) {
            throw new IllegalArgumentException("Latency cannot be negative");
        }

        LatencyMetric latency = latencies.get(metricName);

        if (latency == null) {
            latency = createLatencyMetric(metricName);
        }

        if (latency != null) {
            latency.record(milliseconds);
        }
    }

    // Returns a consolidated snapshot of the metrics accumulated so far.
    // The collector remains available while the export is being generated.
    public String export() {
        StringBuilder result = new StringBuilder();

        // TreeMap is used only for the snapshot so the output is ordered.
        Map<String, Long> counterSnapshot = new TreeMap<>();
        counters.forEach((name, value) -> counterSnapshot.put(name, value.sum()));

        Map<String, LatencySnapshot> latencySnapshot = new TreeMap<>();
        latencies.forEach((name, value) -> latencySnapshot.put(name, value.snapshot()));

        result.append("=== PERFORMANCE METRICS ===\n");

        result.append("\nCounters:\n");
        if (counterSnapshot.isEmpty()) {
            result.append("  No counters registered.\n");
        } else {
            counterSnapshot.forEach((name, value) ->
                    result.append("  ")
                            .append(name)
                            .append(": ")
                            .append(value)
                            .append('\n'));
        }

        result.append("\nLatencies:\n");
        if (latencySnapshot.isEmpty()) {
            result.append("  No latencies registered.\n");
        } else {
            latencySnapshot.forEach((name, value) ->
                    result.append("  ")
                            .append(name)
                            .append(": count=")
                            .append(value.count())
                            .append(", average=")
                            .append(String.format("%.2f", value.averageMilliseconds()))
                            .append(" ms\n"));
        }

        result.append("\nRegistered metrics: ")
                .append(counterSnapshot.size() + latencySnapshot.size())
                .append('/')
                .append(MAX_METRICS)
                .append('\n');

        result.append("Dropped metric registrations: ")
                .append(droppedMetrics.sum())
                .append('\n');

        return result.toString();
    }

    // Only creation takes this lock; updates to existing metrics do not.
    private synchronized LongAdder createCounter(String metricName) {
        // Another thread may have created it after the first get().
        LongAdder existing = counters.get(metricName);
        if (existing != null) {
            return existing;
        }

        if (counters.size() + latencies.size() >= MAX_METRICS) {
            droppedMetrics.increment();
            return null;
        }

        LongAdder created = new LongAdder();
        counters.put(metricName, created);
        return created;
    }

    private synchronized LatencyMetric createLatencyMetric(String metricName) {
        LatencyMetric existing = latencies.get(metricName);
        if (existing != null) {
            return existing;
        }

        if (counters.size() + latencies.size() >= MAX_METRICS) {
            droppedMetrics.increment();
            return null;
        }

        LatencyMetric created = new LatencyMetric();
        latencies.put(metricName, created);
        return created;
    }

    private void validateMetricName(String metricName) {
        if (metricName == null || metricName.isBlank()) {
            throw new IllegalArgumentException("Metric name cannot be null or blank");
        }
    }
}

// Stores aggregated latency information instead of every measurement.
// Memory usage therefore remains constant for each registered metric.
class LatencyMetric {
    private final LongAdder count = new LongAdder();
    private final LongAdder totalMilliseconds = new LongAdder();

    public void record(long milliseconds) {
        count.increment();
        totalMilliseconds.add(milliseconds);
    }

    public LatencySnapshot snapshot() {
        long currentCount = count.sum();
        long currentTotal = totalMilliseconds.sum();

        double average = currentCount == 0
                ? 0.0
                : (double) currentTotal / currentCount;

        return new LatencySnapshot(currentCount, average);
    }
}

record LatencySnapshot(long count, double averageMilliseconds) {
}
