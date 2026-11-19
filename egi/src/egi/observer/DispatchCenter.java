// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.observer;

import egi.Trace;
import egi.coritec.Dispatch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// OBSERVER: the concrete Subject. Receives each dispatch once and tells every subscribed observer.
// It does not know what the observers do with the data: it only calls onDispatch().
public class DispatchCenter implements DispatchSubject {

    private final List<DispatchObserver> observers = new ArrayList<>();
    private final Set<String> seenKeys = new HashSet<>(); // dispatches already notified
    private final Trace trace = Trace.getInstance();

    @Override
    public synchronized void subscribe(DispatchObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            trace.step("CENTRAL", "", "info", "alta: " + observer.name());
        }
    }

    @Override
    public synchronized void unsubscribe(DispatchObserver observer) {
        if (observers.remove(observer)) {
            trace.step("CENTRAL", "", "info", "baja: " + observer.name());
        }
    }

    public synchronized boolean isSubscribed(DispatchObserver observer) {
        return observers.contains(observer);
    }

    // Entry point. Returns false if the dispatch was already received.
    public synchronized boolean receive(Dispatch dispatch) {
        // The same dispatch can arrive twice: drop it here so no observer counts it twice
        if (!seenKeys.add(dispatch.key())) {
            trace.step("CENTRAL", dispatch.reportId(), "duplicate",
                    "repetido (" + dispatch.key() + "), no se notifica");
            return false;
        }
        trace.step("CENTRAL", dispatch.reportId(), "ok",
                "despacho nuevo, notifico a " + observers.size() + " observador(es)");
        notifyObservers(dispatch);
        return true;
    }

    // Tell everyone. If one fails, report it and go on with the rest (no retries).
    // Public because it is part of the Subject interface; receive() is the one that calls it, after the duplicate check.
    @Override
    public synchronized void notifyObservers(Dispatch dispatch) {
        // Copy: an observer could subscribe/unsubscribe while we loop
        for (DispatchObserver observer : List.copyOf(observers)) {
            try {
                observer.onDispatch(dispatch);
            } catch (RuntimeException e) {
                trace.step("OBSERVER", dispatch.reportId(), "error",
                        observer.name() + " falló: " + e.getMessage() + " (sigo con los demás)");
            }
        }
    }

    // Start over (dashboard "reiniciar" button)
    public synchronized void reset() {
        seenKeys.clear();
    }
}
