package e2;

// ALBANES, Luciano Joaquín

class DatabaseManager {
    // The singleton instance, created the instance here to set it as final.
    private final static DatabaseManager instance = new DatabaseManager();

    // here is the connection to the db (the resource that manages)
    // Should be a connection to database, in this example, is just a boolean.
    // false <=> No connected
    private boolean connected;


    // Constructor
    private DatabaseManager() {
        connected = false;
    }


    // PUBLIC METHODS

    public static DatabaseManager getInstance() {
        return instance;
    }

    public synchronized boolean isConnected() {
        return connected;
    }
    
    // open close
    public synchronized void openConnection() {
        if (!connected) {
            // The connection was closed previously. So open it.
            connected = true;
        }
    }

    public synchronized void closeConnection() {
        if (connected) {
            // The connection was open previously. So close it.
            connected = false;
        }
    }

    // Executor of SQL
    // ExecuteUpdate is the same. This directly runs sql into the db.
    public synchronized String executeQuery(String str) {
        if(isConnected() == false) {
            throw new IllegalStateException("The DB connection is closed.");
        } else {
            return ("Consulta Ejecutada correctamente (" + str + ").");
        }
    }

}



public class DatabaseManagerExample {
    public static void main(String[] args) {
        DatabaseManager dbm = DatabaseManager.getInstance();
        
        // Open Connection
        System.out.println("Connection: " + dbm.isConnected());
        dbm.openConnection();
        System.out.println("Connection: " + dbm.isConnected());

        // Execute Query
        System.out.println(dbm.executeQuery("SELECT * FROM USERS;"));

        // Close connection from another reference to the singleton instance
        DatabaseManager dbm2 = DatabaseManager.getInstance();
        dbm2.closeConnection();

        // Should throw error
        try {
            System.out.println(dbm.executeQuery("SELECT * FROM USERS;"));
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        // Check if both references point the same instance.
        System.out.println("Same instance?: " + (dbm == dbm2));
    }
} 