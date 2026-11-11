package e4;

// ALBANES, Luciano Joaquín

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LoanManager {
    private static final LoanManager INSTANCE = new LoanManager();

    // Active loans by copy id (each physical copy has its own id).
    private final Map<String, Loan> activeLoans = new HashMap<>();

    private LoanManager() {}

    public static record Loan(String copyId, String memberId, LocalDate date) {
        @Override
        public String toString() {
            return copyId + " -> " + memberId + " (" + date + ")";
        }
    }

    // Public Methods
    public static LoanManager getInstance() {
        return INSTANCE;
    }

    public synchronized void lend(String copyId, String memberId, LocalDate date) {
        if (activeLoans.containsKey(copyId)) {
            throw new IllegalStateException("Copy " + copyId + " is already lent.");
        }
        activeLoans.put(copyId, new Loan(copyId, memberId, date));
    }

    public synchronized void giveBack(String copyId) {
        if (!activeLoans.containsKey(copyId)) {
            throw new IllegalStateException("Copy " + copyId + " has no active loan.");
        }
        activeLoans.remove(copyId);
    }

    public synchronized List<Loan> getLoansByMember(String memberId) {
        return activeLoans.values().stream()
            .filter(loan -> loan.memberId().equals(memberId))
            .toList();
    }

    public synchronized int getActiveLoanCount() {
        return activeLoans.size();
    }
}

public class LoanManagerExample {
    public static void main(String[] args) {
        LoanManager desk1 = LoanManager.getInstance();
        LoanManager desk2 = LoanManager.getInstance();

        System.out.println("Same instance? " + (desk1 == desk2));

        // Two copies of the same title
        desk1.lend("B101-1", "S100", LocalDate.of(2026, 11, 10));
        desk1.lend("B101-2", "S100", LocalDate.of(2026, 11, 10));

        // Desk 2 tries to lend the same physical copy
        try {
            desk2.lend("B101-1", "S200", LocalDate.of(2026, 11, 10));
        } catch (IllegalStateException e) {
            System.out.println("Desk 2: " + e.getMessage());
        }

        System.out.println("S100 loans: " + desk2.getLoansByMember("S100"));

        // Return from desk 2, now desk 2 can lend it
        desk2.giveBack("B101-1");
        desk2.lend("B101-1", "S200", LocalDate.of(2026, 11, 11));
        System.out.println("S200 loans: " + desk1.getLoansByMember("S200"));

        // Return without active loan is rejected and state stays the same
        int before = desk1.getActiveLoanCount();
        try {
            desk1.giveBack("B999-1");
        } catch (IllegalStateException e) {
            System.out.println("Desk 1: " + e.getMessage());
        }
        System.out.println("Active loans before/after: " + before + "/" + desk1.getActiveLoanCount());
    }
}
