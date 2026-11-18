package e2;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

public class InterfaceSyncExample {
    public static void main(String[] args) {
        // Create publisher
        PlayingStatusPublisher pub = new PlayingStatusPublisher();

        // Create 3 subscribers and subscribe them
        FooterBar sub1 = new FooterBar();
        Widget sub2 = new Widget();
        FloatingWindow sub3 = new FloatingWindow();

        pub.subscribe(sub1);
        pub.subscribe(sub2);
        pub.subscribe(sub3);

        // Update status in publisher (calls notifySubscribers() -> update())
        System.out.println("Subscribed all 3 UI elements and set to Playing: Superestrella - Aitana\n");
        pub.updateSongTitle("Superestrella - Aitana");
        pub.updateStatus(PlayingStatus.PLAYING);

        // Print each subscriber
        System.out.println("FooterBar: " + sub1.getStatus() + " | " + sub1.getSongTitle());
        System.out.println("Widget: " + sub2.getStatus() + " | " + sub2.getSongTitle());
        System.out.println("FloatingWindow: " + sub3.getStatus() + " | " + sub3.getSongTitle());

        // Unsubscribe sub1
        pub.unsubscribe(sub1);

        // Update status in publisher (calls notifySubscribers() -> update())
        System.out.println("\nUnsubscribed FooterBar and updated to Paused with Colombiana - Airbag.");
        pub.updateSongTitle("Colombiana - Airbag");
        pub.updateStatus(PlayingStatus.PAUSED);

        // Print info for each subscriber
        System.out.println("FooterBar: " + sub1.getStatus() + " | " + sub1.getSongTitle());
        System.out.println("Widget: " + sub2.getStatus() + " | " + sub2.getSongTitle());
        System.out.println("FloatingWindow: " + sub3.getStatus() + " | " + sub3.getSongTitle());
    }
}

// Subject (Publisher)
class PlayingStatusPublisher {
    private PlayingStatus status;
    private String songTitle;

    private final Set<PlayingStatusSubscriber> subscribers = new LinkedHashSet<>();

    public void updateStatus(PlayingStatus status) {
        this.status = status;
        notifySubscribers();
    }

    public void updateSongTitle(String songTitle) {
        this.songTitle = songTitle;
        notifySubscribers();
    }

    // Publisher logic/methods
    public void notifySubscribers() {
        for (PlayingStatusSubscriber subscriber : new ArrayList<>(subscribers)) { // Safe for unsuscribe()
            try {
                subscriber.update(status, songTitle);
            } catch (Exception e) {
                System.out.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(PlayingStatusSubscriber o)   {subscribers.add(o);}
    public void unsubscribe(PlayingStatusSubscriber o) {subscribers.remove(o);}
}


// Contract
interface PlayingStatusSubscriber {
    void update(PlayingStatus status, String songTitle);
}

// Observer (Subscriber)

class FooterBar implements PlayingStatusSubscriber {
    PlayingStatus status;
    String songTitle;

    public PlayingStatus getStatus() {
        return status;
    }

    public String getSongTitle() {
        return songTitle;
    }

    @Override
    public void update(PlayingStatus status, String songTitle) {
        this.status = status;
        this.songTitle = songTitle;
    }
}

class Widget implements PlayingStatusSubscriber {
    PlayingStatus status;
    String songTitle;

    public PlayingStatus getStatus() {
        return status;
    }

    public String getSongTitle() {
        return songTitle;
    }

    @Override
    public void update(PlayingStatus status, String songTitle) {
        this.status = status;
        this.songTitle = songTitle;
    }
}

class FloatingWindow implements PlayingStatusSubscriber {
    PlayingStatus status;
    String songTitle;

    public PlayingStatus getStatus() {
        return status;
    }

    public String getSongTitle() {
        return songTitle;
    }

    @Override
    public void update(PlayingStatus status, String songTitle) {
        this.status = status;
        this.songTitle = songTitle;
    }
}

// Playing Status Enum
enum PlayingStatus {
    PLAYING,
    PAUSED,
    STOPPED
}