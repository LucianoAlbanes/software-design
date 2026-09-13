// ALBANES, Luciano Joaquín

import java.util.ArrayList;
import java.util.List;
import java.time.Instant;


class EventLogger {
    private static final EventLogger INSTANCE = new EventLogger();
    private final List<String> log = new ArrayList<>();

    private EventLogger() {
        
    }

    // Emum with log levels
    public static enum Level {
    INFO,
    WARNING,
    ERROR
    }

    // Public Methods
    public static EventLogger getInstance() {
        return INSTANCE;
    }

    // Sync to prevent race conditions at logging.
    public synchronized String logMessage(EventLogger.Level level, String msg) {
        StringBuilder sb = new StringBuilder(); 

        sb.append("[")
            .append(level)
            .append("] ")
            .append(Instant.now())
            .append(" | ")
            .append(msg);

        String logEntry = sb.toString();
        log.add(logEntry);

        return logEntry;
    }

    // As string, to protect the List object.
    public synchronized String getLog() {
        return log.toString();
    }

}



public class Main {
    public static void main(String[] args) {

        // Logger 1 add log
        EventLogger logger1 = EventLogger.getInstance();
        System.out.println(
            logger1.logMessage(EventLogger.Level.INFO, "System Started. Unit1")
        );

        // Logger 2 add log
        EventLogger logger2 = EventLogger.getInstance();
        System.out.println(
            logger2.logMessage(EventLogger.Level.INFO, "System Started. Unit2")
        );

        // Check if are the same
        System.out.println("Are both references the same instance? " + (logger1 == logger2));
        
        // Print log (is an array)
        System.out.println(logger1.getLog());
    }
}
