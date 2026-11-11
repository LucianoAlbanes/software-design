package e2;

public class DatabaseExample {
    public static void main(String[] args) {
        ConnectionCreator conn1 = new SQLiteConnectionCreator();
        ConnectionCreator conn2 = new PostgreSQLConnectionCreator();
        
        System.out.println(conn1.createConnection().query("SELECT 1;"));
        System.out.println(conn2.createConnection().query("SELECT 2;"));
    }   
}

// Document Classes and interface.

interface Connection {
    String query(String query);
}

class SQLiteConnection implements Connection {
    public String query(String query) {
        return "SQLite query: " + query;
    }
}

class PostgreSQLConnection implements Connection {
    public String query(String query) {
        return "PostregSQL query: " + query;
    }
}

// Abstract Connectior Creator and concrete Creator

abstract class ConnectionCreator {
    abstract public Connection createConnection();
}

class SQLiteConnectionCreator extends ConnectionCreator {
    @Override
    public Connection createConnection() {
        return new SQLiteConnection();
    }
}


class PostgreSQLConnectionCreator extends ConnectionCreator {
    @Override
    public Connection createConnection() {
        return new PostgreSQLConnection();
    }
}