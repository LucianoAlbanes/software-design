package e7;

// ALBANES, Luciano Joaquín

import java.util.ArrayList;
import java.util.List;

class TicketDispenser {
    private static final TicketDispenser INSTANCE = new TicketDispenser();

    // Private, only changes through nextTicket().
    private int lastNumber = 0;
    private final List<Ticket> history = new ArrayList<>();

    private TicketDispenser() {}

    public static record Ticket(int number, String terminal) {
        @Override
        public String toString() {
            return "#" + number + " (" + terminal + ")";
        }
    }

    // Public Methods
    public static TicketDispenser getInstance() {
        return INSTANCE;
    }

    public synchronized Ticket nextTicket(String terminal) {
        lastNumber++;
        Ticket ticket = new Ticket(lastNumber, terminal);
        history.add(ticket);
        return ticket;
    }

    public synchronized int getLastNumber() {
        return lastNumber;
    }

    public synchronized List<Ticket> getHistory() {
        return List.copyOf(history);
    }
}

public class TicketDispenserExample {
    public static void main(String[] args) {
        String[] terminals = {"Terminal 1", "Terminal 2", "Terminal 3"};

        // 21 sequential requests, alternating terminals
        for (int i = 0; i < 21; i++) {
            TicketDispenser dispenser = TicketDispenser.getInstance();
            dispenser.nextTicket(terminals[i % terminals.length]);
        }

        TicketDispenser dispenser = TicketDispenser.getInstance();
        System.out.println("Last number: " + dispenser.getLastNumber());
        System.out.println("Last number again: " + dispenser.getLastNumber());
        System.out.println("History: " + dispenser.getHistory());

        // Numbers must be 1, 2, 3... with no repeats
        boolean consecutive = true;
        List<TicketDispenser.Ticket> history = dispenser.getHistory();
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).number() != i + 1) consecutive = false;
        }
        System.out.println("Consecutive and unique? " + consecutive);
    }
}
