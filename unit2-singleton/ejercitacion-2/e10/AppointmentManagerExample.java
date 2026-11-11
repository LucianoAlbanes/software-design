package e10;

// ALBANES, Luciano Joaquín

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class initialization creates the Singleton safely, even with multiple threads.
class AppointmentManager {
    private static final AppointmentManager INSTANCE = new AppointmentManager();

    // Loaded slots (insertion order) and the patient of each booked one.
    private final List<Slot> schedule = new ArrayList<>();
    private final Map<Slot, String> bookings = new HashMap<>();

    private AppointmentManager() {}

    public static record Slot(String professional, LocalDate date, LocalTime time) {}

    // Public Methods
    public static AppointmentManager getInstance() {
        return INSTANCE;
    }

    public synchronized void addSlot(String professional, LocalDate date, LocalTime time) {
        Slot slot = new Slot(professional, date, time);
        if (!schedule.contains(slot)) {
            schedule.add(slot);
        }
    }

    // Check and book under the same lock: atomic.
    public synchronized boolean book(String patient, String professional, LocalDate date, LocalTime time) {
        Slot slot = new Slot(professional, date, time);
        if (!schedule.contains(slot) || bookings.containsKey(slot)) {
            return false;
        }
        bookings.put(slot, patient);
        return true;
    }

    public synchronized boolean cancel(String professional, LocalDate date, LocalTime time) {
        return bookings.remove(new Slot(professional, date, time)) != null;
    }

    public synchronized List<String> getAgenda(String professional, LocalDate date) {
        List<String> agenda = new ArrayList<>();
        for (Slot slot : schedule) {
            if (slot.professional().equals(professional) && slot.date().equals(date)) {
                String patient = bookings.get(slot);
                agenda.add(slot.time() + " " + (patient == null ? "FREE" : patient));
            }
        }
        return agenda;
    }
}

public class AppointmentManagerExample {
    public static void main(String[] args) throws InterruptedException {
        String doctor = "Dra. Pérez";
        LocalDate date = LocalDate.of(2026, 11, 20);

        AppointmentManager manager = AppointmentManager.getInstance();
        manager.addSlot(doctor, date, LocalTime.of(9, 0));
        manager.addSlot(doctor, date, LocalTime.of(9, 20));
        manager.addSlot(doctor, date, LocalTime.of(9, 40));

        // 20 threads, from reception and self-service, try the same slot
        Thread[] requests = new Thread[20];
        boolean[] success = new boolean[requests.length];
        AppointmentManager[] seen = new AppointmentManager[requests.length];

        for (int i = 0; i < requests.length; i++) {
            int index = i;
            String component = (i % 2 == 0) ? "Reception" : "Self-service";
            requests[i] = new Thread(() -> {
                seen[index] = AppointmentManager.getInstance();
                success[index] = seen[index].book("Patient " + (index + 1) + " (" + component + ")",
                        doctor, date, LocalTime.of(9, 0));
            });
            requests[i].start();
        }
        for (Thread request : requests) {
            request.join();
        }

        int successCount = 0;
        boolean sameInstance = true;
        for (int i = 0; i < requests.length; i++) {
            if (success[i]) successCount++;
            if (seen[i] != manager) sameInstance = false;
        }
        System.out.println("Same instance in all threads? " + sameInstance);
        System.out.println("Successful bookings for 09:00: " + successCount + "/" + requests.length);

        AppointmentManager reception = AppointmentManager.getInstance();
        AppointmentManager selfService = AppointmentManager.getInstance();
        reception.book("Patient 21 (Reception)", doctor, date, LocalTime.of(9, 20));
        System.out.println("Self-service agenda: " + selfService.getAgenda(doctor, date));

        // Cancel a nonexistent appointment
        System.out.println("Cancel 09:40 (free): " + selfService.cancel(doctor, date, LocalTime.of(9, 40)));
        System.out.println("Reception agenda: " + reception.getAgenda(doctor, date));

        // Cancel and free the slot
        System.out.println("Cancel 09:00: " + selfService.cancel(doctor, date, LocalTime.of(9, 0)));
        System.out.println("Reception agenda: " + reception.getAgenda(doctor, date));
    }
}
