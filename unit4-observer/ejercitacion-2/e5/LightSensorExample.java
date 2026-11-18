package e5;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

public class LightSensorExample {
    public static void main(String[] args) {
        // Create publishers (one sensor per side of the house)
        LightSensor frontSensor = new LightSensor("Front");
        LightSensor backSensor  = new LightSensor("Back");

        // Create subscribers
        Blinds frontBlinds = new Blinds("Living room");
        Blinds backBlinds  = new Blinds("Bedroom");
        IndoorLights lights = new IndoorLights();

        // Each blind listens to the sensor of its side, the lights listen to both
        frontSensor.subscribe(frontBlinds);
        backSensor.subscribe(backBlinds);
        frontSensor.subscribe(lights);
        backSensor.subscribe(lights);

        System.out.println("Strong sun on the front (900 lux)...");
        frontSensor.updateLux(900);

        System.out.println("\nCloudy on the back (200 lux)...");
        backSensor.updateLux(200);
    }
}

// Subject (Publisher)
class LightSensor {
    private final String side;
    private int lux = 0;

    private final Set<LightObserver> subscribers = new LinkedHashSet<>();

    public LightSensor(String side) {
        this.side = side;
    }

    // Simulates a new reading of the sensor
    public void updateLux(int lux) {
        this.lux = lux;
        notifySubscribers();
    }

    // Publisher logic/methods
    private void notifySubscribers() {
        for (LightObserver subscriber : new ArrayList<>(subscribers)) { // Safe for unsubscribe()
            try {
                subscriber.update(side, lux);
            } catch (Exception e) {
                System.err.println("Failed to update: " + subscriber.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(LightObserver o)   {subscribers.add(o);}
    public void unsubscribe(LightObserver o) {subscribers.remove(o);}
}


// Contract
interface LightObserver {
    void update(String side, int lux);
}

// Observer (Subscriber)

class Blinds implements LightObserver {
    private final String room;

    public Blinds(String room) {
        this.room = room;
    }

    @Override
    public void update(String side, int lux) {
        String action = (lux > 700) ? "closing" : "opening";
        System.out.println("[Blinds " + room + "] " + action + " (" + lux + " lux)");
    }
}

class IndoorLights implements LightObserver {
    @Override
    public void update(String side, int lux) {
        int power = (lux > 700) ? 20 : 80;
        System.out.println("[IndoorLights] " + side + " sensor says " + lux + " lux -> lights at " + power + "%");
    }
}
