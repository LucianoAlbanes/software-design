package e10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AccountRiskExample {
    public static void main(String[] args) {
        // Create publisher
        RiskPublisher pub = new RiskPublisher();

        // Create 3 subscribers and subscribe them
        pub.subscribe(new MoneyCheck());
        pub.subscribe(new LegalCheck());
        pub.subscribe(new RegulatorNotice());

        System.out.println("Account ACME-01 changed to MEDIUM");
        pub.changeRisk("ACME-01", RiskLevel.MEDIUM);

        System.out.println("\nAccount ACME-02 changed to HIGH (RegulatorNotice fails)");
        pub.changeRisk("ACME-02", RiskLevel.HIGH);

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
    public synchronized void changeRisk(String accountId, RiskLevel level) {
        notifySubscribers(accountId, level);
    }

    // Publisher logic/methods
    private void notifySubscribers(String accountId, RiskLevel level) {
        for (RiskSubscriber subscriber : subscribers) {
            String name = subscriber.getClass().getSimpleName();
            try {
                subscriber.update(accountId, level);
                log.add(accountId + " | " + name + " | OK");
            } catch (Exception e) {
                // if one fails, the rest still run
                log.add(accountId + " | " + name + " | ERROR: " + e.getMessage());
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
    void update(String accountId, RiskLevel level);
}

// Observer (Subscriber)

class MoneyCheck implements RiskSubscriber {
    @Override
    public void update(String accountId, RiskLevel level) {
        System.out.println("[MoneyCheck] Checking money moves of " + accountId);
    }
}

class LegalCheck implements RiskSubscriber {
    @Override
    public void update(String accountId, RiskLevel level) {
        System.out.println("[LegalCheck] Checking account " + accountId + " (" + level + ")");
    }
}

// Fails with HIGH accounts, to show the try-catch
class RegulatorNotice implements RiskSubscriber {
    @Override
    public void update(String accountId, RiskLevel level) {
        if (level == RiskLevel.HIGH) {
            throw new IllegalStateException("regulator server not responding");
        }
        System.out.println("[RegulatorNotice] Notice sent for " + accountId);
    }
}
