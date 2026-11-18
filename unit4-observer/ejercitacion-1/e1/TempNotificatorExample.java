package e1;

import java.util.LinkedHashSet;
import java.util.Set;

public class TempNotificatorExample {
    public static void main(String[] args) {
        // Create publisher
        TempPublisher pub = new TempPublisher();

        // Create 3 subscribers and subscribe them
        InformativeScreen sub1 = new InformativeScreen();
        InformativeScreen sub2 = new InformativeScreen();
        InformativeScreen sub3 = new InformativeScreen();

        pub.subscribe(sub1);
        pub.subscribe(sub2);
        pub.subscribe(sub3);

        // Update temp in publisher (calls notifySubscribers() -> update())
        System.out.println("Subcribed all 3 screens and updated temp to 42.5...");
        pub.updateTemperature(42.50);

        // Print temp in each subscriber
        System.out.println("Sub1 temp: " + sub1.getTemperature());
        System.out.println("Sub2 temp: " + sub2.getTemperature());
        System.out.println("Sub3 temp: " + sub3.getTemperature());

        // Unsubscribe sub1
        pub.unsubscribe(sub1);

        // Update temp in publisher (calls notifySubscribers() -> update())
        System.out.println("Removed sub1 and updated temp to 35.");
        pub.updateTemperature(35);

        // Print temp in each subscriber
        System.out.println("Sub1 temp: " + sub1.getTemperature());
        System.out.println("Sub2 temp: " + sub2.getTemperature());
        System.out.println("Sub3 temp: " + sub3.getTemperature());
    }
}

// Subject (Publisher)
class TempPublisher {
    double temperature = 0;

    private final Set<TempObserver> subscribers = new LinkedHashSet<>();

    public void updateTemperature(double temperature) {
        this.temperature = temperature;
        notifySubscribers();
    }

    // Publisher logic/methods
    public void notifySubscribers() {
        for (TempObserver subscriber : subscribers) {
            try {
                subscriber.update(temperature);
            } catch (Exception e) {
                System.out.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(TempObserver o)   {subscribers.add(o);}
    public void unsubscribe(TempObserver o) {subscribers.remove(o);}
}


// Contract
interface TempObserver {
    void update(double temperature);
}

// Observer (Subscriber)

class InformativeScreen implements TempObserver {
    double temperature = 0;

    public double getTemperature() {
        return temperature;
    }

    @Override
    public void update(double temperature) {
        this.temperature = temperature;
    }
}

