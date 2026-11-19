// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.observers;

import egi.coritec.Dispatch;
import egi.observer.DispatchObserver;

// OBSERVER: concrete observer 3, only for the demo.
// Always fails on purpose, to show that the other observers still get the dispatch.
public class FaultyObserver implements DispatchObserver {

    @Override
    public String name() {
        return "Observador con falla (prueba)";
    }

    @Override
    public void onDispatch(Dispatch d) {
        throw new IllegalStateException("falla inducida para la demo");
    }
}
