package e2;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

public class NetworkStatusExample {
    public static void main(String[] args) {
        // Create publisher
        NetworkMonitor pub = new NetworkMonitor();

        // Create 2 subscribers and subscribe them
        ToolbarIcon sub1 = new ToolbarIcon();
        StatusBarText sub2 = new StatusBarText();

        pub.subscribe(sub1);
        pub.subscribe(sub2);

        // Change status (calls notifySubscribers() -> update())
        System.out.println("Network CONNECTED");
        pub.updateStatus(ConnectionStatus.CONNECTED);
        System.out.println("Icon: " + sub1.getIcon() + " | Text: " + sub2.getText());

        System.out.println("\nNetwork UNSTABLE");
        pub.updateStatus(ConnectionStatus.UNSTABLE);
        System.out.println("Icon: " + sub1.getIcon() + " | Text: " + sub2.getText());

        // Unsubscribe the icon
        pub.unsubscribe(sub1);

        System.out.println("\nRemoved ToolbarIcon. Network DISCONNECTED");
        pub.updateStatus(ConnectionStatus.DISCONNECTED);
        System.out.println("Icon: " + sub1.getIcon() + " | Text: " + sub2.getText());
    }
}

enum ConnectionStatus {
    CONNECTED,
    DISCONNECTED,
    UNSTABLE
}

// Subject (Publisher)
class NetworkMonitor {
    private ConnectionStatus status = ConnectionStatus.DISCONNECTED;

    private final Set<NetworkSubscriber> subscribers = new LinkedHashSet<>();

    public void updateStatus(ConnectionStatus status) {
        this.status = status;
        notifySubscribers();
    }

    // Publisher logic/methods
    private void notifySubscribers() {
        for (NetworkSubscriber subscriber : new ArrayList<>(subscribers)) { // Safe for unsubscribe()
            try {
                subscriber.update(status);
            } catch (Exception e) {
                System.out.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(NetworkSubscriber o)   {subscribers.add(o);}
    public void unsubscribe(NetworkSubscriber o) {subscribers.remove(o);}
}


// Contract
interface NetworkSubscriber {
    void update(ConnectionStatus status);
}

// Observer (Subscriber)

class ToolbarIcon implements NetworkSubscriber {
    private String icon = "grey";

    public String getIcon() {
        return icon;
    }

    @Override
    public void update(ConnectionStatus status) {
        icon = switch (status) {
            case CONNECTED    -> "green";
            case UNSTABLE     -> "yellow";
            case DISCONNECTED -> "red";
        };
    }
}

class StatusBarText implements NetworkSubscriber {
    private String text = "";

    public String getText() {
        return text;
    }

    @Override
    public void update(ConnectionStatus status) {
        text = "Network: " + status;
    }
}
