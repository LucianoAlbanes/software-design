package e3;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;


public class EventSubsExample {
    public static void main(String[] args) {
        // Create publisher
        EventPublisher pub = new EventPublisher();

        // Create 2 subscribers and subscribe them
        AuditModule sub1 = new AuditModule();
        LogModule sub2 = new LogModule();

        pub.subscribe(sub1);
        pub.subscribe(sub2);

        // send something
        System.out.println("Sending an event...");
        pub.sendEvent("Alta empleado: Fulano");

        // Unsubscribe sub2
        pub.unsubscribe(sub2);

        // send something more
        System.out.println("\nUnsuscribe sub2 and sent something more.");
        pub.sendEvent("Baja empleado: Miriam");

    }
}

// Subject (Publisher)
class EventPublisher {

    private final Set<EventObserver> subscribers = new LinkedHashSet<>();

    public void sendEvent(String event) {
        // Could be just notifySubscribers... but Single Responsibility Principle (SRP)
        notifySubscribers(event);
    }

    // Publisher logic/methods
    public void notifySubscribers(String event) {
        for (EventObserver subscriber : new ArrayList<>(subscribers)) {
            try {
                subscriber.update(event);
            } catch (Exception e) {
                System.out.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(EventObserver o)   {subscribers.add(o);}
    public void unsubscribe(EventObserver o) {subscribers.remove(o);}
}


// Contract
interface EventObserver {
    void update(String event);
}

// Observer (Subscriber)

class AuditModule implements EventObserver {
    // log to console.
    @Override
    public void update(String event) {
        System.out.println("[Audit Module] Event received: " + event);
    }
}

class LogModule implements EventObserver {
    // disk store - persistant
    @Override
    public void update(String event) {
        System.out.println("[Log Module] Event stored in disk.");
        // blah blah blah store to disk event.
    }
}
