package e10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RiskComplianceExample {
    public static void main(String[] args) {
        // Create publisher
        RiskPublisher pub = new RiskPublisher();

        // Create 3 subscribers and subscribe them
        pub.subscribe(new LegalCheck());
        pub.subscribe(new MoneyCheck());
        pub.subscribe(new ReportService());

        System.out.println("Client C-001 changed to MEDIUM");
        pub.changeRisk("C-001", RiskLevel.MEDIUM);

        System.out.println("\nClient C-002 changed to HIGH (ReportService fails)");
        pub.changeRisk("C-002", RiskLevel.HIGH);

        // Print the log
        System.out.println("\nLog:");
        for (String line : pub.getLog()) {
            System.out.println(line);
        }
    }
}

enum RiskLevel {
    LOW,
    MEDIUM,
    HIGH
}

// Subject (Publisher)
class RiskPublisher {
    // Safe list for threads
    private final List<RiskSubscriber> subscribers = new CopyOnWriteArrayList<>();
    private final List<String> log = new ArrayList<>();

    // synchronized: one change at a time
    public synchronized void changeRisk(String clientId, RiskLevel level) {
        notifySubscribers(clientId, level);
    }

    // Publisher logic/methods
    private void notifySubscribers(String clientId, RiskLevel level) {
        for (RiskSubscriber subscriber : subscribers) {
            String name = subscriber.getClass().getSimpleName();
            try {
                subscriber.update(clientId, level);
                log.add(clientId + " | " + name + " | OK");
            } catch (Exception e) {
                // if one fails, the rest still run
                log.add(clientId + " | " + name + " | ERROR: " + e.getMessage());
            }
        }
    }

    // Copy of the log, can't be changed from outside
    public synchronized List<String> getLog() {
        return List.copyOf(log);
    }

    public void subscribe(RiskSubscriber o)   {subscribers.add(o);}
    public void unsubscribe(RiskSubscriber o) {subscribers.remove(o);}
}


// Contract
interface RiskSubscriber {
    void update(String clientId, RiskLevel level);
}

// Observer (Subscriber)

class LegalCheck implements RiskSubscriber {
    @Override
    public void update(String clientId, RiskLevel level) {
        System.out.println("[LegalCheck] Checking client " + clientId + " (" + level + ")");
    }
}

class MoneyCheck implements RiskSubscriber {
    @Override
    public void update(String clientId, RiskLevel level) {
        System.out.println("[MoneyCheck] Checking money moves of " + clientId);
    }
}

// Fails with HIGH clients, to show the try-catch
class ReportService implements RiskSubscriber {
    @Override
    public void update(String clientId, RiskLevel level) {
        if (level == RiskLevel.HIGH) {
            throw new IllegalStateException("can't make report for HIGH");
        }
        System.out.println("[ReportService] Report done for " + clientId);
    }
}
