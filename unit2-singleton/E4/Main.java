// ALBANES, Luciano Joaquin

import java.util.HashMap;
import java.util.Map;

class UserCache {
    // Lazy-Load Pattern. Initialization-on-demand holder idiom
    // https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Holder {
        private static final UserCache INSTANCE = new UserCache();
    } 

    // Here is the dictionary that will hold the cache
    private final Map<String, String> cacheDictionary = new HashMap<>();

    // Constructor
    private UserCache() {}


    // Public
    public static UserCache getInstance() {
        return Holder.INSTANCE;
    }

    // Store
    public String put(String key, String value) {
        // Delegates the behavior to Map.put(s)
        // Inserts a new value or replaces the existing one.
        // Returns the previous value, or null if the key was absent.
        return cacheDictionary.put(key, value);
    }

    // Retrive
    public String get(String key) {
        // If not exsists, returns null
        return cacheDictionary.get(key);
    }

    // Delete
    public String remove(String key) {
        // Returns the removes value, or null
        return cacheDictionary.remove(key);
    }

    // ContainsKey
    public boolean containsKey(String key) {
        return cacheDictionary.containsKey(key);
    }
}



public class Main {
    public static void main(String[] args) {
        // getInstance
        UserCache cache = UserCache.getInstance();

        // Pass test
        System.out.println("Add new user: " + cache.put("username", "luciano"));
        System.out.println("username: " + cache.get("username"));
        System.out.println("Remove User: " + cache.remove("username"));
        System.out.println("Key still exists?: " + cache.containsKey("username"));

    }
}