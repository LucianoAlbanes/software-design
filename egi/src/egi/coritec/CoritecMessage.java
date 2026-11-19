// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.coritec;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// What the controller sends in each POST, already read.
// The body looks like this (real one, 3/11/2026):
//   datos={"sn":"0001","method":"report_fill","report_id":"5784",
//          "fill_data":{"node":"4","time":"2026-11-02 23:41:14","unit_price":"965.000",
//                       "sale":"7344.00","volume":"7.61","totalizer":"67403.57","ibutton_id":""}}
// method can be "report_fill" (a dispatch) or "report_notif" (a notice from the device).
public record CoritecMessage(String serial, String method, String reportId, Map<String, String> fields) {

    private static final String PREFIX = "datos=";

    // Every value comes as a string: "key":"value". The keys inside fill_data and
    // notif_data never repeat the outer ones, so one flat map is enough.
    private static final Pattern PAIR = Pattern.compile("\"(\\w+)\":\"([^\"]*)\"");

    // Factory: builds the message from the raw body. Returns null if it is not a CORITEC body.
    public static CoritecMessage parse(String body) {
        if (body == null || !body.startsWith(PREFIX)) {
            return null;
        }
        Map<String, String> fields = new LinkedHashMap<>();
        Matcher m = PAIR.matcher(body.substring(PREFIX.length()));
        while (m.find()) {
            fields.put(m.group(1), m.group(2));
        }
        if (!fields.containsKey("method")) {
            return null;
        }
        return new CoritecMessage(fields.get("sn"), fields.get("method"), fields.get("report_id"), fields);
    }

    public boolean isDispatch() {
        return "report_fill".equals(method);
    }

    // Only for report_fill
    public Dispatch toDispatch() {
        return new Dispatch(
                serial,
                reportId,
                Integer.parseInt(fields.get("node")),
                fields.get("time"),
                new BigDecimal(fields.get("unit_price")),
                new BigDecimal(fields.get("sale")),
                new BigDecimal(fields.get("volume")),
                new BigDecimal(fields.get("totalizer")));
    }

    // For report_notif: which notice it is
    public String event() {
        return fields.getOrDefault("event", "?");
    }

    // Acknowledgement sent back to the controller
    public String ackJson() {
        return "{\"sn\":\"" + serial + "\",\"method\":\"" + method
                + "\",\"report_id\":\"" + reportId + "\",\"result\":\"saved\"}";
    }
}
