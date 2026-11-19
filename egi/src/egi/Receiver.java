// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi;

import egi.coritec.CoritecMessage;
import egi.coritec.Dispatch;
import egi.observer.DispatchCenter;

// Takes the raw body of a POST, reads it and, if it is a dispatch, hands it to the center.
// The console demo and the HTTP server both use this, so the data follows the same path.
public class Receiver {

    public enum Result { NEW, DUPLICATE, NOTIFICATION, INVALID }

    private final DispatchCenter center;
    private final Trace trace = Trace.getInstance();

    public Receiver(DispatchCenter center) {
        this.center = center;
    }

    public Result handle(String body) {
        CoritecMessage msg = CoritecMessage.parse(body);
        if (msg == null) {
            trace.step("LECTOR", "", "error", "cuerpo no reconocido, se descarta");
            return Result.INVALID;
        }

        // report_notif is not a sale: it must not be added as m3
        if (!msg.isDispatch()) {
            trace.step("LECTOR", msg.reportId(), "ignored",
                    msg.method() + " (" + msg.event() + "): no es un despacho, no se notifica");
            return Result.NOTIFICATION;
        }

        Dispatch d = msg.toDispatch();
        trace.step("LECTOR", d.reportId(), "ok",
                "report_fill -> Dispatch(surtidor " + d.pump() + ", " + d.volume() + " m3, " + d.time() + ")");
        return center.receive(d) ? Result.NEW : Result.DUPLICATE;
    }
}
