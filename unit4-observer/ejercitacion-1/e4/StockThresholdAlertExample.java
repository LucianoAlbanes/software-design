package e4;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

public class StockThresholdAlertExample {
    public static void main(String[] args) {
        // Create product and a stockAlert (fires when stock drops below 5)
        Product product = new Product("Phone 18", 7,5);

        // Create two subscribers
        PurchaseDepartment sub1 = new PurchaseDepartment();
        MarketingSystem sub2    = new MarketingSystem();

        product.getStockAlertPub().subscribe(sub1);
        product.getStockAlertPub().subscribe(sub2);


        // sell 1 (stock 6)
        System.out.println("Selling one unit (should not fire)");
        product.deltaStock(-1);

        // sell 2 (stock 5, at the threshold but not below it)
        System.out.println("Selling another unit (should not fire)");
        product.deltaStock(-1);

        // sell 3 (stock 4, crosses below the threshold)
        System.out.println("Selling another unit (should fire)");
        product.deltaStock(-1);

        // sell 4 (stock 3, already below: no repeated alert)
        System.out.println("Selling another unit (should not fire again)");
        product.deltaStock(-1);

    }
}

class Product {
    private String name;
    private int stock;
    private StockThresholdAlertPublisher stockAlertPub;

    // Constructor
    public Product(String name, int initialStock, int threshold) {
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial stock can't be negative.");
        }
        this.name = name;
        this.stock = initialStock;
        this.stockAlertPub = new StockThresholdAlertPublisher(this, threshold);
    }

    // Setters and getters
    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Update stock (and notify). Only way to change the stock (no setStock).
    public void deltaStock(int amount) {
        // Valid value
        if (stock + amount < 0) {
            throw new IllegalArgumentException("Final stock can't be negative.");
        }

        // Update stock
        int previousStock = stock;
        stock += amount;

        // notify if the threshold was crossed
        stockAlertPub.checkThreshold(previousStock);
    }

    // getter of the publisher (singleton like, private and only one instance per product.)
    // Only exposes subscribe/unsubscribe: nobody outside can fire an alert.
    public StockThresholdAlertSubject getStockAlertPub() {
        return stockAlertPub;
    }
}


// Public side of the publisher (what the outside world can use)
interface StockThresholdAlertSubject {
    void subscribe(StockThresholdAlertSubscriber subscriber);
    void unsubscribe(StockThresholdAlertSubscriber subscriber);
}

// Subject (Publisher)
class StockThresholdAlertPublisher implements StockThresholdAlertSubject {
    private final int threshold;
    private final Product product;

    private final Set<StockThresholdAlertSubscriber> subscribers = new LinkedHashSet<>();


    public StockThresholdAlertPublisher(Product p, int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold can't be negative.");
        }
        this.product = p;
        this.threshold = threshold;
    }


    // Fires only when the stock goes from >= threshold to < threshold
    void checkThreshold(int previousStock) {
        int currentStock = product.getStock();

        if (previousStock >= threshold && currentStock < threshold) {
            notifySubscribers(currentStock);
        }
    }



    // Publisher logic/methods
    private void notifySubscribers(int currentStock) {
        for (StockThresholdAlertSubscriber subscriber : new ArrayList<>(subscribers)) { // Safe for unsubscribe()
            try {
                subscriber.alert(product, currentStock);
            } catch (Exception e) {
                System.err.println("Failed to update: " + subscriber.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }

    @Override
    public void subscribe(StockThresholdAlertSubscriber subscriber)   {subscribers.add(subscriber);}
    @Override
    public void unsubscribe(StockThresholdAlertSubscriber subscriber) {subscribers.remove(subscriber);}
}


// Contract
interface StockThresholdAlertSubscriber {
    void alert(Product product, int currentStock);
}

// Observer (Subscriber)

// Decides on its own: orders enough units to get back to its restock level
class PurchaseDepartment implements StockThresholdAlertSubscriber {
    private static final int RESTOCK_LEVEL = 20;

    @Override
    public void alert(Product product, int currentStock) {
        int toOrder = RESTOCK_LEVEL - currentStock;
        System.out.println("[" + this.getClass().getSimpleName() + "] Product: " + product.getName() + " has low stock (" + currentStock + ") -> ordering " + toOrder + " units");
    }
}

// Decides on its own: pauses ads if sold out, otherwise starts a clearance campaign
class MarketingSystem implements StockThresholdAlertSubscriber {
    @Override
    public void alert(Product product, int currentStock) {
        String action = (currentStock == 0) ? "pausing ads (sold out)" : "starting clearance campaign";
        System.out.println("[" + this.getClass().getSimpleName() + "] Product: " + product.getName() + " has low stock (" + currentStock + ") -> " + action);
    }
}