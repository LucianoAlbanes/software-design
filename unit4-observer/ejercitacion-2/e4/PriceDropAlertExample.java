package e4;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;

public class PriceDropAlertExample {
    public static void main(String[] args) {
        // Create product (publisher)
        Product product = new Product("Headphones", 100000);

        // Create 2 subscribers (channels of the wishlist)
        EmailAlert sub1 = new EmailAlert();
        WebPanel sub2   = new WebPanel();

        product.subscribe(sub1);
        product.subscribe(sub2);

        System.out.println("Price goes up to 110000 (should not fire)");
        product.updatePrice(110000);

        System.out.println("\nPrice drops to 104500 (-5%)");
        product.updatePrice(104500);

        System.out.println("\nPrice drops to 83600 (-20%)");
        product.updatePrice(83600);
    }
}

// Subject (Publisher)
class Product {
    private final String name;
    private double price; // private, only changes through updatePrice()

    private final Set<PriceDropSubscriber> subscribers = new LinkedHashSet<>();

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice(double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }

        double oldPrice = price;
        price = newPrice;

        // only notify if the price went down
        if (newPrice < oldPrice) {
            double discount = (oldPrice - newPrice) / oldPrice * 100;
            notifySubscribers(oldPrice, discount);
        }
    }

    // Publisher logic/methods
    private void notifySubscribers(double oldPrice, double discount) {
        for (PriceDropSubscriber subscriber : new ArrayList<>(subscribers)) { // Safe for unsubscribe()
            try {
                subscriber.priceDropped(this, oldPrice, discount);
            } catch (Exception e) {
                System.err.println("Failed to update: " + subscriber.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(PriceDropSubscriber o)   {subscribers.add(o);}
    public void unsubscribe(PriceDropSubscriber o) {subscribers.remove(o);}
}


// Contract
interface PriceDropSubscriber {
    void priceDropped(Product product, double oldPrice, double discount);
}

// Observer (Subscriber)

// Only sends a mail if the discount is 10% or more
class EmailAlert implements PriceDropSubscriber {
    @Override
    public void priceDropped(Product product, double oldPrice, double discount) {
        if (discount >= 10) {
            System.out.printf("[EmailAlert] Mail: %s is now $%.0f (-%.0f%%)%n", product.getName(), product.getPrice(), discount);
        } else {
            System.out.println("[EmailAlert] Small discount, no mail");
        }
    }
}

// Always shows it on the web panel
class WebPanel implements PriceDropSubscriber {
    @Override
    public void priceDropped(Product product, double oldPrice, double discount) {
        System.out.printf("[WebPanel] %s: $%.0f -> $%.0f (-%.0f%%)%n", product.getName(), oldPrice, product.getPrice(), discount);
    }
}
