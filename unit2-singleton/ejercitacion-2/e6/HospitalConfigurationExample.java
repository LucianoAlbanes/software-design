package e6;

// ALBANES, Luciano Joaquín

import java.time.LocalTime;

class HospitalConfiguration {
    // Lazy-Load Pattern. Initialization-on-demand holder idiom
    private static class Holder {
        private static final HospitalConfiguration INSTANCE = new HospitalConfiguration();
    }

    // Only to show how many times the constructor runs.
    private static int createdInstances = 0;

    private String hospitalName = "Hospital Central";
    private LocalTime openingTime = LocalTime.of(8, 0);
    private LocalTime closingTime = LocalTime.of(20, 0);
    private int appointmentMinutes = 20;

    private HospitalConfiguration() {
        createdInstances++;
    }

    // Public Methods
    public static HospitalConfiguration getInstance() {
        return Holder.INSTANCE;
    }

    public static int getCreatedInstances() {
        return createdInstances;
    }

    // Getters and Setters
    public synchronized String getHospitalName() {
        return hospitalName;
    }

    public synchronized LocalTime getOpeningTime() {
        return openingTime;
    }

    public synchronized LocalTime getClosingTime() {
        return closingTime;
    }

    public synchronized int getAppointmentMinutes() {
        return appointmentMinutes;
    }

    public synchronized void setHospitalName(String hospitalName) {
        if (hospitalName == null || hospitalName.isBlank()) {
            throw new IllegalArgumentException("Hospital name can't be blank.");
        }
        this.hospitalName = hospitalName;
    }

    // Both times together, so closing is always after opening.
    public synchronized void setOpeningHours(LocalTime openingTime, LocalTime closingTime) {
        if (!closingTime.isAfter(openingTime)) {
            throw new IllegalArgumentException("Closing time must be after opening time.");
        }
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public synchronized void setAppointmentMinutes(int appointmentMinutes) {
        if (appointmentMinutes <= 0) {
            throw new IllegalArgumentException("Appointment duration must be greater than 0.");
        }
        this.appointmentMinutes = appointmentMinutes;
    }
}

public class HospitalConfigurationExample {
    public static void main(String[] args) {
        System.out.println("Instances before first call: " + HospitalConfiguration.getCreatedInstances());

        HospitalConfiguration reception = HospitalConfiguration.getInstance();
        System.out.println("Instances after first call: " + HospitalConfiguration.getCreatedInstances());

        HospitalConfiguration office = HospitalConfiguration.getInstance();
        HospitalConfiguration.getInstance();
        System.out.println("Instances after more calls: " + HospitalConfiguration.getCreatedInstances());
        System.out.println("Same instance? " + (reception == office));

        // Reception changes, office sees it
        reception.setHospitalName("Hospital Regional San Martín");
        reception.setOpeningHours(LocalTime.of(7, 30), LocalTime.of(21, 0));
        reception.setAppointmentMinutes(15);
        System.out.println("Office: " + office.getHospitalName() + " | "
                + office.getOpeningTime() + "-" + office.getClosingTime() + " | "
                + office.getAppointmentMinutes() + " min");

        // Invalid values
        try {
            reception.setOpeningHours(LocalTime.of(18, 0), LocalTime.of(9, 0));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            reception.setAppointmentMinutes(0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
