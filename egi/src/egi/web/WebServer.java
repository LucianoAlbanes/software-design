// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import egi.Receiver;
import egi.Trace;
import egi.coritec.Captures;
import egi.coritec.Captures.Capture;
import egi.coritec.CoritecMessage;
import egi.coritec.Dispatch;
import egi.observer.DispatchCenter;
import egi.observer.DispatchObserver;
import egi.observers.DispatchLog;
import egi.observers.FaultyObserver;
import egi.observers.PumpTotals;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

// HTTP receiver + dashboard. Same objects as the console demo, now behind a real POST.
//   POST /method                -> what the CORITEC controller calls (body "datos={...}")
//   GET  /                      -> dashboard (web/index.html)
//   GET  /api/state?since=N     -> observers, log, totals and trace lines after N (JSON)
//   GET  /api/captures          -> the 13 real captures, so the dashboard can send them
//   POST /api/observer?id=X     -> subscribe/unsubscribe observer X
//   POST /api/reset             -> empty log, totals and seen dispatches
// Run from the egi folder:  java -cp out egi.web.WebServer [port]
public class WebServer {

    private final DispatchCenter center = new DispatchCenter();
    private final DispatchLog log = new DispatchLog();
    private final PumpTotals totals = new PumpTotals();
    private final Receiver receiver = new Receiver(center);
    private final Map<String, DispatchObserver> observers = new LinkedHashMap<>();
    private final Map<Receiver.Result, Integer> counters = new LinkedHashMap<>();
    private final Trace trace = Trace.getInstance();
    private volatile String lastBody = ""; // last POST body, to show it raw in the dashboard

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8090;
        new WebServer().start(port);
    }

    private void start(int port) throws IOException {
        observers.put("registro", log);
        observers.put("acumulador", totals);
        observers.put("falla", new FaultyObserver()); // starts unsubscribed
        center.subscribe(log);
        center.subscribe(totals);
        resetCounters();

        // Only this Mac (127.0.0.1). To open it from the phone: nb-expose <port>
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/method", this::coritecPost);
        server.createContext("/api/state", this::state);
        server.createContext("/api/captures", this::captures);
        server.createContext("/api/observer", this::toggleObserver);
        server.createContext("/api/reset", this::reset);
        server.createContext("/", this::dashboard);
        server.start();
        System.out.println("Receptor CORITEC en http://127.0.0.1:" + port + "/method");
        System.out.println("Dashboard en        http://127.0.0.1:" + port + "/\n");
    }

    // The same request the controller sends: POST, form-urlencoded, body "datos={...}"
    private void coritecPost(HttpExchange ex) throws IOException {
        if (!ex.getRequestMethod().equals("POST")) {
            send(ex, 405, "text/plain", "solo POST");
            return;
        }
        String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.US_ASCII);
        String type = ex.getRequestHeaders().getFirst("Content-Type");
        trace.step("HTTP", "", "info", "POST /method, " + body.length() + " bytes, " + type);
        lastBody = body;

        Receiver.Result result = receiver.handle(body);
        synchronized (counters) {
            counters.merge(result, 1, Integer::sum);
        }
        CoritecMessage msg = CoritecMessage.parse(body);
        if (msg == null) {
            send(ex, 400, "text/plain", "cuerpo no reconocido");
        } else {
            send(ex, 200, "application/json", msg.ackJson()); // acuse, also for duplicates
        }
    }

    private void state(HttpExchange ex) throws IOException {
        int since = 0;
        String query = ex.getRequestURI().getQuery();
        if (query != null && query.startsWith("since=")) {
            since = Integer.parseInt(query.substring(6));
        }

        StringJoiner obs = new StringJoiner(",", "[", "]");
        observers.forEach((id, o) -> obs.add("{\"id\":" + q(id) + ",\"name\":" + q(o.name())
                + ",\"subscribed\":" + center.isSubscribed(o) + "}"));

        StringJoiner rows = new StringJoiner(",", "[", "]");
        for (Dispatch d : log.all()) {
            rows.add("{\"reportId\":" + q(d.reportId()) + ",\"pump\":" + d.pump() + ",\"time\":" + q(d.time())
                    + ",\"volume\":" + d.volume() + ",\"sale\":" + d.sale() + ",\"totalizer\":" + d.totalizer() + "}");
        }

        StringJoiner pumps = new StringJoiner(",", "{", "}");
        totals.totals().forEach((pump, m3) -> pumps.add(q(String.valueOf(pump)) + ":" + m3));

        StringJoiner steps = new StringJoiner(",", "[", "]");
        for (Trace.Step s : trace.since(since)) {
            steps.add("{\"seq\":" + s.seq() + ",\"hour\":" + q(s.hour()) + ",\"stage\":" + q(s.stage())
                    + ",\"ref\":" + q(s.ref()) + ",\"result\":" + q(s.result()) + ",\"text\":" + q(s.text()) + "}");
        }

        StringJoiner count = new StringJoiner(",", "{", "}");
        synchronized (counters) {
            counters.forEach((r, n) -> count.add(q(r.name()) + ":" + n));
        }

        String json = "{\"observers\":" + obs + ",\"log\":" + rows + ",\"totals\":" + pumps
                + ",\"counters\":" + count + ",\"lastBody\":" + q(lastBody) + ",\"trace\":" + steps + "}";
        send(ex, 200, "application/json", json);
    }

    private void captures(HttpExchange ex) throws IOException {
        StringJoiner list = new StringJoiner(",", "[", "]");
        for (Capture c : Captures.load()) {
            CoritecMessage m = CoritecMessage.parse(c.body());
            String extra = m.isDispatch()
                    ? ",\"pump\":" + m.fields().get("node") + ",\"volume\":" + q(m.fields().get("volume"))
                    : ",\"event\":" + q(m.event());
            list.add("{\"n\":" + c.n() + ",\"method\":" + q(m.method())
                    + ",\"reportId\":" + q(m.reportId()) + extra + ",\"body\":" + q(c.body()) + "}");
        }
        send(ex, 200, "application/json", list.toString());
    }

    private void toggleObserver(HttpExchange ex) throws IOException {
        String query = ex.getRequestURI().getQuery();
        DispatchObserver o = query == null ? null : observers.get(query.replace("id=", ""));
        if (!ex.getRequestMethod().equals("POST") || o == null) {
            send(ex, 400, "text/plain", "uso: POST /api/observer?id=registro|acumulador|falla");
            return;
        }
        if (center.isSubscribed(o)) {
            center.unsubscribe(o);
        } else {
            center.subscribe(o);
        }
        send(ex, 204, "text/plain", "");
    }

    private void reset(HttpExchange ex) throws IOException {
        // back to the start: log and totals subscribed, faulty observer out
        center.subscribe(log);
        center.subscribe(totals);
        center.unsubscribe(observers.get("falla"));
        center.reset();
        log.clear();
        totals.clear();
        resetCounters();
        lastBody = "";
        trace.step("CENTRAL", "", "info", "reinicio: registro, totales y despachos vistos en cero");
        send(ex, 204, "text/plain", "");
    }

    private void dashboard(HttpExchange ex) throws IOException {
        if (!ex.getRequestURI().getPath().equals("/")) {
            send(ex, 404, "text/plain", "no existe");
            return;
        }
        send(ex, 200, "text/html; charset=utf-8", Files.readString(Path.of("web", "index.html")));
    }

    private void resetCounters() {
        synchronized (counters) {
            for (Receiver.Result r : Receiver.Result.values()) {
                counters.put(r, 0);
            }
        }
    }

    // --- small helpers ---

    private static void send(HttpExchange ex, int status, String type, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", type);
        ex.getResponseHeaders().set("Cache-Control", "no-store");
        ex.sendResponseHeaders(status, status == 204 ? -1 : bytes.length);
        if (status != 204) {
            ex.getResponseBody().write(bytes);
        }
        ex.close();
    }

    // JSON string with quotes escaped
    private static String q(String s) {
        return "\"" + (s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"")) + "\"";
    }
}
