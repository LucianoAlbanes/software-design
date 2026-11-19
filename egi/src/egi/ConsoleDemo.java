// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi;

import egi.coritec.Captures;
import egi.coritec.Captures.Capture;
import egi.observer.DispatchCenter;
import egi.observers.DispatchLog;
import egi.observers.FaultyObserver;
import egi.observers.PumpTotals;
import java.math.BigDecimal;
import java.util.List;

// Client: runs the whole story in the console with the 13 real POSTs of 3/11/2026.
// Run from the egi folder:  java -cp out egi.ConsoleDemo
public class ConsoleDemo {

    private static List<Capture> captures;
    private static Receiver receiver;

    public static void main(String[] args) throws Exception {
        captures = Captures.load();

        // Subject and observers
        DispatchCenter center = new DispatchCenter();
        DispatchLog log = new DispatchLog();
        PumpTotals totals = new PumpTotals();
        FaultyObserver faulty = new FaultyObserver();
        receiver = new Receiver(center);

        title("1. Alta de los dos observadores");
        center.subscribe(log);
        center.subscribe(totals);

        title("2. Llegan los primeros despachos (capturas 1 a 5)");
        send(1, 2, 3, 4, 5);

        title("3. Llegan dos notificaciones del equipo (capturas 6 y 7)");
        send(6, 7);

        title("4. El equipo vuelve a mandar el 5784 (captura 1 otra vez)");
        send(1);

        title("5. Baja del acumulador. Llegan las capturas 8 y 9");
        center.unsubscribe(totals);
        send(8, 9);

        title("6. Vuelve el acumulador y se suma un observador que falla. Llega la 10");
        center.subscribe(totals);
        center.subscribe(faulty);
        send(10);

        title("7. Sale el observador con falla. Llegan 11, 12 y 13");
        center.unsubscribe(faulty);
        send(11, 12, 13);

        title("Resultado");
        log.print();
        totals.print();

        BigDecimal logM3 = log.all().stream().map(d -> d.volume()).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nEl registro suma " + logM3 + " m3 y el acumulador " + totals.grandTotal() + " m3.");
        System.out.println("Diferencia: " + logM3.subtract(totals.grandTotal())
                + " m3, los despachos 5780 y 5781 que llegaron con el acumulador dado de baja (paso 5).");
    }

    // Hands the chosen captures to the receiver, as if they came by HTTP
    private static void send(int... numbers) {
        for (int n : numbers) {
            Capture c = captures.get(n - 1);
            System.out.println();
            Trace.getInstance().step("ENTRADA", "", "info", "captura " + n + ": " + c.body().length() + " bytes");
            receiver.handle(c.body());
        }
    }

    private static void title(String text) {
        System.out.println("\n=== " + text + " " + "=".repeat(Math.max(3, 70 - text.length())));
    }
}
