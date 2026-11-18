package e3;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

public class GameScoreExample {
    public static void main(String[] args) {
        // Create publisher
        Score pub = new Score();

        // Create 2 subscribers and subscribe them
        GameScreen sub1 = new GameScreen();
        RecordBook sub2 = new RecordBook();

        pub.subscribe(sub1);
        pub.subscribe(sub2);

        System.out.println("Player destroys an obstacle and picks a coin...");
        pub.addPoints(100);
        pub.addPoints(50);

        // Unsubscribe the record book
        pub.unsubscribe(sub2);

        System.out.println("\nRemoved RecordBook. Player picks another coin...");
        pub.addPoints(50);

        System.out.println("\nBest score saved: " + sub2.getBest());
    }
}

// Subject (Publisher)
class Score {
    private int points = 0;

    private final Set<ScoreObserver> subscribers = new LinkedHashSet<>();

    public void addPoints(int amount) {
        points += amount;
        notifySubscribers();
    }

    // Publisher logic/methods
    private void notifySubscribers() {
        for (ScoreObserver subscriber : new ArrayList<>(subscribers)) { // Safe for unsubscribe()
            try {
                subscriber.update(points);
            } catch (Exception e) {
                System.out.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(ScoreObserver o)   {subscribers.add(o);}
    public void unsubscribe(ScoreObserver o) {subscribers.remove(o);}
}


// Contract
interface ScoreObserver {
    void update(int points);
}

// Observer (Subscriber)

class GameScreen implements ScoreObserver {
    @Override
    public void update(int points) {
        System.out.println("[GameScreen] SCORE: " + points);
    }
}

class RecordBook implements ScoreObserver {
    private int best = 0;

    public int getBest() {
        return best;
    }

    @Override
    public void update(int points) {
        if (points > best) {
            best = points;
            System.out.println("[RecordBook] New record: " + best);
        }
    }
}
