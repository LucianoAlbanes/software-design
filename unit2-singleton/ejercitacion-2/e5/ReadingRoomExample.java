package e5;

// ALBANES, Luciano Joaquín

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// Class initialization creates the Singleton safely, even with multiple threads.
class ReadingRoom {
    private static final ReadingRoom INSTANCE = new ReadingRoom();
    private static final int CAPACITY = 10;

    // Set rejects duplicated members, keeps insertion order.
    private final Set<String> reservations = new LinkedHashSet<>();

    private ReadingRoom() {}

    // Public Methods
    public static ReadingRoom getInstance() {
        return INSTANCE;
    }

    // Check and add under the same lock: atomic.
    public synchronized boolean reserve(String memberId) {
        if (reservations.size() >= CAPACITY || reservations.contains(memberId)) {
            return false;
        }
        return reservations.add(memberId);
    }

    public synchronized boolean cancel(String memberId) {
        return reservations.remove(memberId);
    }

    public synchronized List<String> getReservations() {
        return List.copyOf(reservations);
    }

    public synchronized int getAvailableSeats() {
        return CAPACITY - reservations.size();
    }
}

public class ReadingRoomExample {
    public static void main(String[] args) throws InterruptedException {
        Thread[] requests = new Thread[30];
        boolean[] accepted = new boolean[requests.length];
        ReadingRoom[] seen = new ReadingRoom[requests.length];

        for (int i = 0; i < requests.length; i++) {
            int index = i;
            requests[i] = new Thread(() -> {
                seen[index] = ReadingRoom.getInstance();
                accepted[index] = seen[index].reserve("S" + (100 + index));
            });
            requests[i].start();
        }
        for (Thread request : requests) {
            request.join();
        }

        ReadingRoom room = ReadingRoom.getInstance();
        int acceptedCount = 0;
        boolean sameInstance = true;
        for (int i = 0; i < requests.length; i++) {
            if (accepted[i]) acceptedCount++;
            if (seen[i] != room) sameInstance = false;
        }

        System.out.println("Same instance in all threads? " + sameInstance);
        System.out.println("Accepted: " + acceptedCount + " | Rejected: " + (requests.length - acceptedCount));
        System.out.println("Active: " + room.getReservations());
        System.out.println("Available seats: " + room.getAvailableSeats());

        // Duplicated reservation
        String first = room.getReservations().get(0);
        System.out.println("Duplicated " + first + " accepted? " + room.reserve(first));

        // Cancel and recover the seat
        System.out.println("Cancel " + first + ": " + room.cancel(first));
        System.out.println("Available seats: " + room.getAvailableSeats());

        // Cancel nonexistent reservation
        System.out.println("Cancel S999: " + room.cancel("S999"));
        System.out.println("Available seats: " + room.getAvailableSeats());
    }
}
