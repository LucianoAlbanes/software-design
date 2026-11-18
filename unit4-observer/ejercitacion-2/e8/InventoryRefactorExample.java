package e8;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

/*
 * BEFORE (legacy, coupled):
 *
 * class Product {
 *     private BillingService billing = new BillingService();
 *     private StatsService stats = new StatsService();
 *
 *     public void removeStock(int amount) {
 *         stock -= amount;
 *         billing.createInvoice(name, amount);   // direct call
 *         stats.registerSale(name, amount);      // direct call
 *     }
 * }
 *
 * AFTER: Product only changes its stock and notifies the listeners.
 */
public class InventoryRefactorExample {
    public static void main(String[] args) {
        // Create publisher
        Product product = new Product("Mate", 10);

        // Product doesn't know these classes, we subscribe them here
        BillingModule sub1 = new BillingModule();
        StatsModule sub2   = new StatsModule();

        product.subscribe(sub1);
        product.subscribe(sub2);

        System.out.println("Selling 3 units...");
        product.changeStock(-3);

        System.out.println("\nRestocking 5 units...");
        product.changeStock(5);

        System.out.println("\nStats: " + sub2.getSold() + " sold, " + sub2.getRestocked() + " restocked");
    }
}

// Subject (Publisher)
class Product {
    private final String name;
    private int stock;

    // No BillingService or StatsService here anymore
    private final Set<StockListener> listeners = new LinkedHashSet<>();

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    // negative = sale, positive = restock
    public void changeStock(int amount) {
        if (stock + amount < 0) {
            throw new IllegalArgumentException("Not enough stock.");
        }
        stock += amount;
        notifyListeners(amount);
    }

    // Publisher logic/methods
    private void notifyListeners(int amount) {
        for (StockListener listener : new ArrayList<>(listeners)) { // Safe for unsubscribe()
            try {
                listener.onStockChanged(name, amount, stock);
            } catch (Exception e) {
                System.err.println("Failed to update: " + listener.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(StockListener o)   {listeners.add(o);}
    public void unsubscribe(StockListener o) {listeners.remove(o);}
}


// Contract
interface StockListener {
    void onStockChanged(String product, int amount, int newStock);
}

// Observer (Subscriber)

// Only bills sales
class BillingModule implements StockListener {
    @Override
    public void onStockChanged(String product, int amount, int newStock) {
        if (amount < 0) {
            System.out.println("[BillingModule] Invoice for " + (-amount) + " x " + product);
        }
    }
}

class StatsModule implements StockListener {
    private int sold = 0;
    private int restocked = 0;

    public int getSold() {
        return sold;
    }

    public int getRestocked() {
        return restocked;
    }

    @Override
    public void onStockChanged(String product, int amount, int newStock) {
        if (amount < 0) {
            sold += -amount;
        } else {
            restocked += amount;
        }
        System.out.println("[StatsModule] " + product + " stock now: " + newStock);
    }
}
