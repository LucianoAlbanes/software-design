package e8;

// ALBANES, Luciano Joaquín

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SupplyRegistry {
    private static final SupplyRegistry INSTANCE = new SupplyRegistry();

    private final List<Movement> movements = new ArrayList<>();
    private final Map<String, Integer> balances = new HashMap<>();

    private SupplyRegistry() {}

    public static enum Sector {
        DEPOSITO,
        CONSULTORIOS,
        LABORATORIO
    }

    public static enum Type {
        ENTRADA,
        SALIDA
    }

    // Immutable: once registered, can't be edited.
    public static record Movement(String supplyCode, Type type, int quantity, Sector sector, Instant date) {
        @Override
        public String toString() {
            return "[" + sector + "] " + type + " " + quantity + " x " + supplyCode;
        }
    }

    // Public Methods
    public static SupplyRegistry getInstance() {
        return INSTANCE;
    }

    public synchronized void register(String supplyCode, Type type, int quantity, Sector sector) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        int balance = getBalance(supplyCode);
        if (type == Type.SALIDA && quantity > balance) {
            throw new IllegalStateException("Not enough " + supplyCode + " (balance " + balance + ").");
        }
        balances.put(supplyCode, type == Type.ENTRADA ? balance + quantity : balance - quantity);
        movements.add(new Movement(supplyCode, type, quantity, sector, Instant.now()));
    }

    public synchronized List<Movement> getMovements() {
        return List.copyOf(movements);
    }

    public synchronized List<Movement> getMovementsBySupply(String supplyCode) {
        return movements.stream()
            .filter(movement -> movement.supplyCode().equals(supplyCode))
            .toList();
    }

    public synchronized List<Movement> getMovementsBySector(Sector sector) {
        return movements.stream()
            .filter(movement -> movement.sector() == sector)
            .toList();
    }

    // Unknown supplies have balance 0, so the first movement must be an ENTRADA.
    public synchronized int getBalance(String supplyCode) {
        return balances.getOrDefault(supplyCode, 0);
    }
}

public class SupplyRegistryExample {
    public static void main(String[] args) {
        SupplyRegistry deposit = SupplyRegistry.getInstance();
        SupplyRegistry offices = SupplyRegistry.getInstance();
        SupplyRegistry laboratory = SupplyRegistry.getInstance();

        System.out.println("Same instance? " + (deposit == offices && offices == laboratory));

        // Initial balances
        deposit.register("GUANTES", SupplyRegistry.Type.ENTRADA, 100, SupplyRegistry.Sector.DEPOSITO);
        deposit.register("JERINGAS", SupplyRegistry.Type.ENTRADA, 50, SupplyRegistry.Sector.DEPOSITO);

        offices.register("GUANTES", SupplyRegistry.Type.SALIDA, 30, SupplyRegistry.Sector.CONSULTORIOS);
        laboratory.register("GUANTES", SupplyRegistry.Type.SALIDA, 20, SupplyRegistry.Sector.LABORATORIO);
        laboratory.register("JERINGAS", SupplyRegistry.Type.SALIDA, 10, SupplyRegistry.Sector.LABORATORIO);

        // Invalid movements
        try {
            offices.register("JERINGAS", SupplyRegistry.Type.SALIDA, 60, SupplyRegistry.Sector.CONSULTORIOS);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        try {
            laboratory.register("ALCOHOL", SupplyRegistry.Type.SALIDA, 1, SupplyRegistry.Sector.LABORATORIO);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        try {
            deposit.register("GUANTES", SupplyRegistry.Type.ENTRADA, -5, SupplyRegistry.Sector.DEPOSITO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("All: " + deposit.getMovements());
        System.out.println("GUANTES: " + deposit.getMovementsBySupply("GUANTES"));
        System.out.println("LABORATORIO: " + deposit.getMovementsBySector(SupplyRegistry.Sector.LABORATORIO));
        System.out.println("Balance GUANTES: " + deposit.getBalance("GUANTES"));
        System.out.println("Balance JERINGAS: " + deposit.getBalance("JERINGAS"));

        // Returned list can't be modified
        try {
            deposit.getMovements().clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("History can't be modified from client code.");
        }
    }
}
