package e7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class VideoChannelsExample {
    public static void main(String[] args) {
        // Create publisher
        ChannelHub pub = new ChannelHub();

        // Create 3 users
        User ana   = new User("Ana");
        User bruno = new User("Bruno");
        User carla = new User("Carla");

        // Each user follows the channels they like
        pub.subscribe("cooking-ch", ana);
        pub.subscribe("tech-ch", ana);
        pub.subscribe("tech-ch", bruno);
        pub.subscribe("music-ch", carla);

        System.out.println("Uploading one video per channel...");
        pub.upload(new Video("tech-ch", "Building a PC", 15));
        pub.upload(new Video("cooking-ch", "Empanadas mendocinas", 8));
        pub.upload(new Video("music-ch", "Live in Mendoza", 60));

        // Ana stops following tech
        pub.unsubscribe("tech-ch", ana);

        System.out.println("\nAna unfollowed tech-ch. Uploading another tech video...");
        pub.upload(new Video("tech-ch", "Linux for beginners", 20));

        System.out.println("\nNobody follows gaming-ch...");
        pub.upload(new Video("gaming-ch", "Speedrun", 30));
    }
}

// Video data sent to the users
record Video(String channelId, String title, int minutes) {}

// Subject (Publisher)
// One set of followers per channel id
class ChannelHub {
    private final Map<String, Set<VideoSubscriber>> followers = new HashMap<>();

    public void upload(Video video) {
        notifyFollowers(video);
    }

    // Publisher logic/methods: only the followers of that channel
    private void notifyFollowers(Video video) {
        Set<VideoSubscriber> list = followers.getOrDefault(video.channelId(), Set.of());

        for (VideoSubscriber subscriber : new ArrayList<>(list)) { // Safe for unsubscribe()
            try {
                subscriber.update(video);
            } catch (Exception e) {
                System.err.println("Failed to update: " + subscriber + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(String channelId, VideoSubscriber o) {
        followers.computeIfAbsent(channelId, k -> new LinkedHashSet<>()).add(o);
    }

    public void unsubscribe(String channelId, VideoSubscriber o) {
        Set<VideoSubscriber> list = followers.get(channelId);
        if (list != null) {
            list.remove(o);
        }
    }
}


// Contract
interface VideoSubscriber {
    void update(Video video);
}

// Observer (Subscriber)

class User implements VideoSubscriber {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(Video video) {
        System.out.println("[" + name + "] Pop-up: new video on " + video.channelId() + ": " + video.title() + " (" + video.minutes() + " min)");
    }
}
