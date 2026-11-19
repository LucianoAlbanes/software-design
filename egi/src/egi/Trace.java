// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// SINGLETON: one trace for the whole program.
// Every class writes here each step of the data (input, reader, center, observers).
// It prints to the console and keeps the lines so the web dashboard can show the same thing.
public final class Trace {

    // Created once when the class loads (eager): thread safe without extra code
    private static final Trace INSTANCE = new Trace();

    private static final DateTimeFormatter HOUR = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    // One line of the trace.
    // stage:  ENTRADA (or HTTP), LECTOR, CENTRAL, OBSERVER
    // ref:    report id the line talks about ("" if none)
    // result: ok, duplicate, ignored, error, info
    public record Step(int seq, String hour, String stage, String ref, String result, String text) {}

    private final List<Step> steps = new ArrayList<>();

    private Trace() {} // nobody else can do "new Trace()"

    public static Trace getInstance() {
        return INSTANCE;
    }

    public synchronized void step(String stage, String ref, String result, String text) {
        steps.add(new Step(steps.size() + 1, LocalTime.now().format(HOUR), stage, ref, result, text));
        System.out.printf("%-10s %-5s %s%n", "[" + stage + "]", ref, text);
    }

    // Lines after "seq" (the dashboard asks only for the new ones)
    public synchronized List<Step> since(int seq) {
        return new ArrayList<>(steps.subList(Math.min(Math.max(seq, 0), steps.size()), steps.size()));
    }
}
