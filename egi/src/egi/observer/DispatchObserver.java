// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.observer;

import egi.coritec.Dispatch;

// OBSERVER: the interface every consumer of dispatches implements.
// The center only knows this, never the concrete classes.
public interface DispatchObserver {

    // Name to show in the console and the dashboard
    String name();

    // Called by the center for each new dispatch (never for a duplicate)
    void onDispatch(Dispatch dispatch);
}
