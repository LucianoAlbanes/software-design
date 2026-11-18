package e7;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewsChannelsExample {
    public static void main(String[] args) {
        // Create publisher
        NewsPortal pub = new NewsPortal();

        // Create 3 readers
        Reader ana   = new Reader("Ana");
        Reader bruno = new Reader("Bruno");
        Reader carla = new Reader("Carla");

        // Each reader subscribes only to what they care about
        pub.subscribe(Category.TECHNOLOGY, ana);
        pub.subscribe(Category.SPORTS, ana);
        pub.subscribe(Category.SPORTS, bruno);
        pub.subscribe(Category.ECONOMY, carla);

        System.out.println("Publishing one article per category...");
        pub.publish(new Article("New M5 chip released", Category.TECHNOLOGY));
        pub.publish(new Article("Godoy Cruz wins the derby", Category.SPORTS));
        pub.publish(new Article("Inflation slows down", Category.ECONOMY));

        // Ana cancels sports
        pub.unsubscribe(Category.SPORTS, ana);

        System.out.println("\nAna unsubscribed from SPORTS. Publishing another sports article...");
        pub.publish(new Article("Mendoza marathon this Sunday", Category.SPORTS));

        // Print each inbox
        System.out.println("\nInboxes:");
        System.out.println("Ana: " + ana.getInbox());
        System.out.println("Bruno: " + bruno.getInbox());
        System.out.println("Carla: " + carla.getInbox());
    }
}

enum Category {
    TECHNOLOGY,
    SPORTS,
    ECONOMY
}

// News object sent to the readers
record Article(String title, Category category) {}

// Subject (Publisher)
// One set of subscribers per category
class NewsPortal {
    private final Map<Category, Set<NewsSubscriber>> subscribers = new EnumMap<>(Category.class);

    public NewsPortal() {
        for (Category category : Category.values()) {
            subscribers.put(category, new LinkedHashSet<>());
        }
    }

    public void publish(Article article) {
        notifySubscribers(article);
    }

    // Publisher logic/methods: only the subscribers of that category
    private void notifySubscribers(Article article) {
        for (NewsSubscriber subscriber : new ArrayList<>(subscribers.get(article.category()))) { // Safe for unsubscribe()
            try {
                subscriber.update(article);
            } catch (Exception e) {
                System.err.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(Category category, NewsSubscriber o)   {subscribers.get(category).add(o);}
    public void unsubscribe(Category category, NewsSubscriber o) {subscribers.get(category).remove(o);}
}


// Contract
interface NewsSubscriber {
    void update(Article article);
}

// Observer (Subscriber)

class Reader implements NewsSubscriber {
    private final String name;
    private final List<String> inbox = new ArrayList<>();

    public Reader(String name) {
        this.name = name;
    }

    public List<String> getInbox() {
        return inbox;
    }

    @Override
    public void update(Article article) {
        inbox.add(article.title());
        System.out.println("[" + name + "] New " + article.category() + " article: " + article.title());
    }
}
