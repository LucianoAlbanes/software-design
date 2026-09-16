package e5;

// ALBANES, Luciano Joaquín

public class HardwareControllerExample {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();

        controller.sendCommand("bla");
        System.out.println(controller.isFree());

        //To simulate a concurrent access to resource, should implement threaded code.

    }

}

class Controller {
    // Instance
    private static final Controller INSTANCE = new Controller();
    
    // DMA Simulator

    // Status
    private boolean free = true;

    private Controller() {}

    private void executeCommand(Object command) {
        // Simulates to execute command.
        System.out.println("Executed: " + command);
    }

    // Public methods

    public static Controller getInstance() {
        return INSTANCE;
    }

    public synchronized void sendCommand(String command) {
        if (!free) {
            throw new IllegalStateException("The resource is occupied.");
        }

        free = false;
        
        try {
            executeCommand(command);
        }
        finally {
            free = true;
        }
    }

    public synchronized boolean isFree() {
        return free;
    }

}
