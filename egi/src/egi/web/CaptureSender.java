// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.web;

import egi.coritec.Captures;
import egi.coritec.Captures.Capture;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// Plays the role of the CORITEC controller: sends the real captures by HTTP,
// with the same body and Content-Type the controller used.
// Run from the egi folder:
//   java -cp out egi.web.CaptureSender                 -> all 13, one per second
//   java -cp out egi.web.CaptureSender 1 2 1           -> only those (1 twice = duplicate)
public class CaptureSender {

    private static final String URL = "http://127.0.0.1:8090/method";
    private static final long PAUSE_MS = 1000;

    public static void main(String[] args) throws Exception {
        List<Capture> all = Captures.load();
        List<Capture> toSend = new ArrayList<>();
        if (args.length == 0) {
            toSend.addAll(all);
        } else {
            for (String a : args) {
                toSend.add(all.get(Integer.parseInt(a) - 1));
            }
        }

        HttpClient client = HttpClient.newHttpClient();
        for (Capture c : toSend) {
            HttpRequest req = HttpRequest.newBuilder(URI.create(URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(c.body()))
                    .build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("captura " + c.n() + " -> " + res.statusCode() + " " + res.body());
            Thread.sleep(PAUSE_MS);
        }
    }
}
