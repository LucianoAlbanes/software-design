// (c) 2026 Luciano J. Albanes. Uso académico. Prohibida su copia o redistribución sin autorización del autor.
package egi.coritec;

import java.math.BigDecimal;

// One fuel dispatch (a "report_fill" from the CORITEC controller).
// Immutable: every observer gets the same data and none can change it.
// Money and m3 use BigDecimal, never double, so 7.61 stays 7.61.
public record Dispatch(
        String serial,       // controller serial number ("sn")
        String reportId,     // id the controller gives to the dispatch
        int pump,            // "node": pump/hose that served the gas
        String time,         // controller clock, UTC-3
        BigDecimal unitPrice,
        BigDecimal sale,     // $ charged
        BigDecimal volume,   // m3
        BigDecimal totalizer // pump counter after the dispatch
) {
    // Same controller + same report id = same dispatch, even if the POST comes twice
    public String key() {
        return serial + "/" + reportId;
    }
}
