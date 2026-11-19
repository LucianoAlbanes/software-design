// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.observer;

import egi.coritec.Dispatch;

// OBSERVER: the publishing side (Subject). Whoever implements this keeps a list
// of DispatchObserver and tells all of them when a new dispatch arrives.
public interface DispatchSubject {

    void subscribe(DispatchObserver observer);

    void unsubscribe(DispatchObserver observer);

    void notifyObservers(Dispatch dispatch);
}
