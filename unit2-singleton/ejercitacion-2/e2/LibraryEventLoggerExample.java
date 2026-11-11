package e2;

// ALBANES, Luciano Joaquín

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;


class LibraryEventLogger {
    private static final LibraryEventLogger INSTANCE = new LibraryEventLogger();
    private final List<LogEntry> log = new ArrayList<>();

    private LibraryEventLogger() {
        
    }

    // Emum with Departments
    public static enum Departments {
    PRESTAMOS,
    DEVOLUCIONES,
    ATENCION_A_SOCIOS
    }

    // One event: department, date/time and description (immutable).
    public static record LogEntry(Departments department, Instant timestamp, String msg) {
        @Override
        public String toString() {
            return "[" + department + "] " + timestamp + " | " + msg;
        }
    }

    // Public Methods
    public static LibraryEventLogger getInstance() {
        return INSTANCE;
    }

    // Sync to prevent race conditions at logging.
    public synchronized String logMessage(LibraryEventLogger.Departments department, String msg) {
        LogEntry entry = new LogEntry(department, Instant.now(), msg);
        log.add(entry);

        return entry.toString();
    }

    // Full history in insertion order. Returns a copy, to protect the List object.
    public synchronized List<LogEntry> getLog() {
        return List.copyOf(log);
    }

    // History filtered by department, keeping insertion order.
    public synchronized List<LogEntry> getLogByDepartment(LibraryEventLogger.Departments department) {
        return log.stream()
            .filter(entry -> entry.department() == department)
            .toList();
    }

    // Total amount of events.
    public synchronized int getEventCount() {
        return log.size();
    }

}



public class LibraryEventLoggerExample {
    public static void main(String[] args) {

        // Logger 1 add log
        LibraryEventLogger logger1 = LibraryEventLogger.getInstance();
        System.out.println(
            logger1.logMessage(LibraryEventLogger.Departments.PRESTAMOS, "Prestamo a Fulano Mingano - B102")
        );

        // Logger 2 add log
        LibraryEventLogger logger2 = LibraryEventLogger.getInstance();
        System.out.println(
            logger2.logMessage(LibraryEventLogger.Departments.ATENCION_A_SOCIOS, "Alta Débito Automático - S100")
        );

        // Logger 3 add log
        LibraryEventLogger logger3 = LibraryEventLogger.getInstance();
        System.out.println(
            logger3.logMessage(LibraryEventLogger.Departments.PRESTAMOS, "Prestamo a Mengano Zutano - B205")
        );

        // Check if are the same
        System.out.println("Are both references the same instance? " + (logger1 == logger2 && logger2 == logger3));

        // Print full log (is a list, insertion order)
        System.out.println(logger1.getLog());

        // Print log filtered by department
        System.out.println(logger1.getLogByDepartment(LibraryEventLogger.Departments.PRESTAMOS));

        // Print total events
        System.out.println("Total events: " + logger1.getEventCount());
    }
}
