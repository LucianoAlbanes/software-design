package e10;

// ALBANES, Luciano Joaquín

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityAuditExample {
    public static void main(String[] args) throws InterruptedException {
        SecurityAuditor auditor = SecurityAuditor.getInstance();
        Thread[] workers = new Thread[4];

        for (int i = 0; i < workers.length; i++) {
            String operatorId = "operator-" + (i + 1);
            workers[i] = new Thread(() -> {
                UUID sessionId = UUID.randomUUID();
                auditor.recordEvent(sessionId, operatorId, "Transfer approved");
                auditor.recordEvent(sessionId, operatorId, "Privileges changed");
            });
            workers[i].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        System.out.println("=== SECURITY AUDIT ===");
        System.out.println("Total records: " + auditor.getTotalRecords());
        auditor.getAuditTrail().forEach(System.out::println);
    }
}

// Class initialization creates the Singleton safely, even with multiple threads.
class SecurityAuditor {
    private static final SecurityAuditor INSTANCE = new SecurityAuditor();

    private final List<AuditEvent> events = new ArrayList<>();

    private SecurityAuditor() {}

    public static SecurityAuditor getInstance() {
        return INSTANCE;
    }

    // One lock protects timestamp generation, sequence assignment and append.
    public synchronized void recordEvent(UUID sessionId, String operatorId, String action) {
        if (sessionId == null || operatorId == null || operatorId.isBlank()
                || action == null || action.isBlank()) {
            throw new IllegalArgumentException("Session, operator and action are required");
        }

        // If the current timestamp is equal to or before the previous one,
        // use the previous timestamp plus one nanosecond.
        Instant timestamp = Instant.now();
        if (!events.isEmpty()) {
            Instant previous = events.get(events.size() - 1).timestamp();
            // Preserve strict order even if the clock repeats or moves backwards.
            if (!timestamp.isAfter(previous)) {
                timestamp = previous.plusNanos(1);
            }
        }

        events.add(new AuditEvent((long) events.size() + 1,
                timestamp, sessionId, operatorId, action));
    }

    public synchronized int getTotalRecords() {
        return events.size();
    }

    public synchronized List<AuditEvent> getAuditTrail() {
        return List.copyOf(events);
    }
}

// All fields are immutable; callers also receive an unmodifiable list copy.
record AuditEvent(long sequence, Instant timestamp, UUID sessionId,
                  String operatorId, String action) {
}
