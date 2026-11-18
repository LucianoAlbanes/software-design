package e9;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerAlertBusExample {
    public static void main(String[] args) {
        // Create the central bus
        AlertBus bus = new AlertBus();

        // Create services (subscribers)
        PagerService pager     = new PagerService();
        AutoScaler scaler      = new AutoScaler();
        BackupService backup   = new BackupService();
        BrokenService broken   = new BrokenService();

        // Each service registers only for the alert types it cares about
        bus.subscribe(AlertType.CPU_HIGH, scaler);
        bus.subscribe(AlertType.CPU_HIGH, pager);
        bus.subscribe(AlertType.DISK_FAILURE, broken);  // fails on purpose, registered first
        bus.subscribe(AlertType.DISK_FAILURE, backup);
        bus.subscribe(AlertType.DISK_FAILURE, pager);

        System.out.println("node-1 publishes CPU_HIGH...");
        bus.publish(new Alert(AlertType.CPU_HIGH, Severity.WARNING, "node-1: CPU at 90%"));

        System.out.println("\nnode-2 publishes DISK_FAILURE (BrokenService throws, the rest still run)...");
        bus.publish(new Alert(AlertType.DISK_FAILURE, Severity.CRITICAL, "node-2: disk /dev/sda failed"));

        System.out.println("\nnode-3 publishes LOW_MEMORY (nobody listens)...");
        bus.publish(new Alert(AlertType.LOW_MEMORY, Severity.WARNING, "node-3: 200 MB free"));
    }
}

enum AlertType {
    CPU_HIGH,
    LOW_MEMORY,
    DISK_FAILURE
}

enum Severity {
    INFO,
    WARNING,
    CRITICAL
}

// Alert: type + severity + text
record Alert(AlertType type, Severity severity, String message) {}

// Subject (Publisher)
class AlertBus {
    // One list of listeners per alert type (safe for threads)
    private final Map<AlertType, List<AlertListener>> listeners = new ConcurrentHashMap<>();

    public void subscribe(AlertType type, AlertListener listener) {
        listeners.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void unsubscribe(AlertType type, AlertListener listener) {
        List<AlertListener> list = listeners.get(type);
        if (list != null) {
            list.remove(listener);
        }
    }

    public void publish(Alert alert) {
        notifyListeners(alert);
    }

    // Publisher logic/methods: only the listeners of that type
    private void notifyListeners(Alert alert) {
        List<AlertListener> list = listeners.getOrDefault(alert.type(), List.of());

        if (list.isEmpty()) {
            System.out.println("[AlertBus] No listeners for " + alert.type());
            return;
        }

        for (AlertListener listener : list) {
            try {
                listener.onAlert(alert);
            } catch (Exception e) {
                // if one fails, the rest still run
                System.out.println("[AlertBus] Failed: " + listener.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }
}


// Contract
interface AlertListener {
    void onAlert(Alert alert);
}

// Observer (Subscriber)

class PagerService implements AlertListener {
    @Override
    public void onAlert(Alert alert) {
        System.out.println("[PagerService] Calling on-duty admin: [" + alert.severity() + "] " + alert.message());
    }
}

class AutoScaler implements AlertListener {
    @Override
    public void onAlert(Alert alert) {
        System.out.println("[AutoScaler] Adding one more server");
    }
}

class BackupService implements AlertListener {
    @Override
    public void onAlert(Alert alert) {
        System.out.println("[BackupService] Restoring last backup");
    }
}

// Simulates a service that is down
class BrokenService implements AlertListener {
    @Override
    public void onAlert(Alert alert) {
        throw new IllegalStateException("service unavailable");
    }
}
