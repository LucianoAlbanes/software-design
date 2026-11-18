package e9;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBusExample {
    public static void main(String[] args) {
        // Create the central bus
        EventBus bus = new EventBus();

        // Create services (subscribers)
        MailService mail       = new MailService();
        BillingService billing = new BillingService();
        BrokenService broken   = new BrokenService();

        // Each service registers only for the event types it cares about
        bus.subscribe("UserRegistered", mail);
        bus.subscribe("PaymentApproved", broken);  // fails on purpose, registered first
        bus.subscribe("PaymentApproved", mail);
        bus.subscribe("PaymentApproved", billing);

        System.out.println("Publishing UserRegistered...");
        bus.publish(new Event("UserRegistered", Map.of("user", "luciano")));

        System.out.println("\nPublishing PaymentApproved (BrokenService throws, the rest still run)...");
        bus.publish(new Event("PaymentApproved", Map.of("user", "luciano", "amount", "15000")));

        System.out.println("\nPublishing OrderShipped (nobody listens)...");
        bus.publish(new Event("OrderShipped", Map.of("order", "A-12")));
    }
}

// Event: type + data
record Event(String type, Map<String, String> payload) {}

// Subject (Publisher)
class EventBus {
    // One list of listeners per event type (safe for threads)
    private final Map<String, List<EventListener>> listeners = new ConcurrentHashMap<>();

    public void subscribe(String type, EventListener listener) {
        listeners.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void unsubscribe(String type, EventListener listener) {
        List<EventListener> list = listeners.get(type);
        if (list != null) {
            list.remove(listener);
        }
    }

    public void publish(Event event) {
        notifyListeners(event);
    }

    // Publisher logic/methods: only the listeners of that type
    private void notifyListeners(Event event) {
        List<EventListener> list = listeners.getOrDefault(event.type(), List.of());

        if (list.isEmpty()) {
            System.out.println("[EventBus] No listeners for " + event.type());
            return;
        }

        for (EventListener listener : list) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                // if one fails, the rest still run
                System.out.println("[EventBus] Failed: " + listener.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }
}


// Contract
interface EventListener {
    void onEvent(Event event);
}

// Observer (Subscriber)

class MailService implements EventListener {
    @Override
    public void onEvent(Event event) {
        System.out.println("[MailService] Sending mail for " + event.type() + " to " + event.payload().get("user"));
    }
}

class BillingService implements EventListener {
    @Override
    public void onEvent(Event event) {
        System.out.println("[BillingService] Invoice created for $" + event.payload().get("amount"));
    }
}

// Simulates a service that is down
class BrokenService implements EventListener {
    @Override
    public void onEvent(Event event) {
        throw new IllegalStateException("service unavailable");
    }
}
