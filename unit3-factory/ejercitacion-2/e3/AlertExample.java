package e3;

public class AlertExample {
    public static void main(String[] args) {
        // CREATORS
        AlertCreator[] creators = {
            new ConsoleAlertCreator(),
            new FileAlertCreator()
        };

        // TEST (With for each, to test inheritance)
        for (AlertCreator creator : creators) {
            Alert alert = creator.createAlert();
            alert.send("CPU usage above 90%");
        }
    }
}


// Interface

interface Alert {
    void send(String message);
}

class ConsoleAlert implements Alert {
    @Override
    public void send(String message) {
        System.out.println("ALERT: " + message);
    }
}

class FileAlert implements Alert {
    @Override
    public void send(String message) {
        // Simulated, it doesn't write a real file
        System.out.println("Saved in alerts.log: " + message);
    }
}

// Creators

abstract class AlertCreator {
    public abstract Alert createAlert();
}

class ConsoleAlertCreator extends AlertCreator {
    @Override
    public Alert createAlert() {
        return new ConsoleAlert();
    }
}

class FileAlertCreator extends AlertCreator {
    @Override
    public Alert createAlert() {
        return new FileAlert();
    }
}
