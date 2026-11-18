package e1;

import java.util.LinkedHashSet;
import java.util.Set;

public class ButtonClickExample {
    public static void main(String[] args) {
        // Create publisher
        Button pub = new Button("Save");

        // Create 2 subscribers and subscribe them
        MainWindow sub1 = new MainWindow();
        ClickCounter sub2 = new ClickCounter();

        pub.subscribe(sub1);
        pub.subscribe(sub2);

        // Click (calls notifySubscribers() -> onClick())
        System.out.println("Clicking the button...");
        pub.click();

        // Unsubscribe sub1
        pub.unsubscribe(sub1);

        System.out.println("\nRemoved MainWindow and clicked again.");
        pub.click();

        System.out.println("\nTotal clicks counted: " + sub2.getClicks());
    }
}

// Subject (Publisher)
class Button {
    private final String label;

    private final Set<ClickListener> subscribers = new LinkedHashSet<>();

    public Button(String label) {
        this.label = label;
    }

    // Simulates the user clicking
    public void click() {
        notifySubscribers();
    }

    // Publisher logic/methods
    public void notifySubscribers() {
        for (ClickListener subscriber : subscribers) {
            try {
                subscriber.onClick(label);
            } catch (Exception e) {
                System.out.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(ClickListener o)   {subscribers.add(o);}
    public void unsubscribe(ClickListener o) {subscribers.remove(o);}
}


// Contract
interface ClickListener {
    void onClick(String buttonLabel);
}

// Observer (Subscriber)

class MainWindow implements ClickListener {
    @Override
    public void onClick(String buttonLabel) {
        System.out.println("[MainWindow] Button '" + buttonLabel + "' clicked -> saving file");
    }
}

class ClickCounter implements ClickListener {
    private int clicks = 0;

    public int getClicks() {
        return clicks;
    }

    @Override
    public void onClick(String buttonLabel) {
        clicks++;
        System.out.println("[ClickCounter] Clicks: " + clicks);
    }
}
