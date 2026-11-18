package e5;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

public class SmokeSensorExample {
    public static void main(String[] args) {
        // Create publishers (one sensor per floor)
        SmokeSensor sensorFloor1 = new SmokeSensor("Floor 1 - Kitchen");
        SmokeSensor sensorFloor2 = new SmokeSensor("Floor 2 - Office 204");

        // Create subscribers
        AlarmCentral alarm         = new AlarmCentral();
        Concierge concierge        = new Concierge();
        SprinklerSystem sprinkler1 = new SprinklerSystem("Floor 1");
        SprinklerSystem sprinkler2 = new SprinklerSystem("Floor 2");

        // Alarm and concierge listen to every sensor
        sensorFloor1.subscribe(alarm);
        sensorFloor1.subscribe(concierge);
        sensorFloor2.subscribe(alarm);
        sensorFloor2.subscribe(concierge);

        // Each sprinkler only listens to the sensor of its own floor
        sensorFloor1.subscribe(sprinkler1);
        sensorFloor2.subscribe(sprinkler2);

        System.out.println("Smoke detected on floor 1...");
        sensorFloor1.detectSmoke();

        System.out.println("\nSmoke detected on floor 2...");
        sensorFloor2.detectSmoke();
    }
}

// Subject (Publisher)
class SmokeSensor {
    private final String location;

    private final Set<SmokeObserver> subscribers = new LinkedHashSet<>();

    public SmokeSensor(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    // Simulates the hardware detecting smoke
    public void detectSmoke() {
        notifySubscribers();
    }

    // Publisher logic/methods
    private void notifySubscribers() {
        for (SmokeObserver subscriber : new ArrayList<>(subscribers)) { // Safe for unsubscribe()
            try {
                subscriber.onSmokeDetected(location);
            } catch (Exception e) {
                System.err.println("Failed to update: " + subscriber.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(SmokeObserver o)   {subscribers.add(o);}
    public void unsubscribe(SmokeObserver o) {subscribers.remove(o);}
}


// Contract
interface SmokeObserver {
    void onSmokeDetected(String location);
}

// Observer (Subscriber)

class AlarmCentral implements SmokeObserver {
    @Override
    public void onSmokeDetected(String location) {
        System.out.println("[AlarmCentral] Siren ON. Emergency at: " + location);
    }
}

class SprinklerSystem implements SmokeObserver {
    private final String zone;

    public SprinklerSystem(String zone) {
        this.zone = zone;
    }

    @Override
    public void onSmokeDetected(String location) {
        System.out.println("[SprinklerSystem " + zone + "] Opening sprinklers at: " + location);
    }
}

class Concierge implements SmokeObserver {
    @Override
    public void onSmokeDetected(String location) {
        System.out.println("[Concierge] Calling firefighters and evacuating: " + location);
    }
}
