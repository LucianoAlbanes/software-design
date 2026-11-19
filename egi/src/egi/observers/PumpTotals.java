// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.observers;

import egi.Trace;
import egi.coritec.Dispatch;
import egi.observer.DispatchObserver;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

// OBSERVER: concrete observer 2. Adds up m3 per pump (in memory).
public class PumpTotals implements DispatchObserver {

    private final Map<Integer, BigDecimal> m3ByPump = new TreeMap<>(); // sorted by pump

    @Override
    public String name() {
        return "Acumulador m3 por surtidor";
    }

    @Override
    public synchronized void onDispatch(Dispatch d) {
        BigDecimal total = m3ByPump.getOrDefault(d.pump(), BigDecimal.ZERO).add(d.volume());
        m3ByPump.put(d.pump(), total);
        Trace.getInstance().step("OBSERVER", d.reportId(), "ok",
                name() + ": surtidor " + d.pump() + " +" + d.volume() + " = " + total + " m3");
    }

    public synchronized Map<Integer, BigDecimal> totals() {
        return new TreeMap<>(m3ByPump);
    }

    public synchronized BigDecimal grandTotal() {
        return m3ByPump.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public synchronized void clear() {
        m3ByPump.clear();
    }

    public synchronized void print() {
        System.out.println("\nm3 por surtidor:");
        m3ByPump.forEach((pump, m3) -> System.out.printf("  surtidor %d: %8s m3%n", pump, m3));
        System.out.printf("  total:      %8s m3%n", grandTotal());
    }
}
