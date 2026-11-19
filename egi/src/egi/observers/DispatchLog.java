// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.observers;

import egi.Trace;
import egi.coritec.Dispatch;
import egi.observer.DispatchObserver;
import java.util.ArrayList;
import java.util.List;

// OBSERVER: concrete observer 1. Keeps every dispatch, in arrival order (in memory).
public class DispatchLog implements DispatchObserver {

    private final List<Dispatch> dispatches = new ArrayList<>();

    @Override
    public String name() {
        return "Registro de despachos";
    }

    @Override
    public synchronized void onDispatch(Dispatch d) {
        dispatches.add(d);
        Trace.getInstance().step("OBSERVER", d.reportId(), "ok",
                name() + ": guardado #" + dispatches.size()
                        + " (surtidor " + d.pump() + ", " + d.volume() + " m3, $" + d.sale() + ")");
    }

    public synchronized List<Dispatch> all() {
        return List.copyOf(dispatches);
    }

    public synchronized void clear() {
        dispatches.clear();
    }

    public synchronized void print() {
        System.out.println("\nRegistro de despachos (" + dispatches.size() + "):");
        System.out.println("  report  surtidor  hora del equipo        m3      importe");
        for (Dispatch d : dispatches) {
            System.out.printf("  %-6s  %-8d  %-19s  %6s  %11s%n",
                    d.reportId(), d.pump(), d.time(), d.volume(), d.sale());
        }
    }
}
