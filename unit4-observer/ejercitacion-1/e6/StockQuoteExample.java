package e6;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class StockQuoteExample {
    public static void main(String[] args) {
        // Create publisher
        Asset pub = new Asset("YPF", 100);

        // Create 3 subscribers and subscribe them
        LineChart sub1       = new LineChart();
        SummaryTable sub2    = new SummaryTable();
        VolatilityAlert sub3 = new VolatilityAlert(5); // alerts if it moves 5% or more

        pub.subscribe(sub1);
        pub.subscribe(sub2);
        pub.subscribe(sub3);

        // Update price (calls notifySubscribers() -> update())
        System.out.println("Price 100 -> 102");
        pub.updatePrice(102);

        System.out.println("\nPrice 102 -> 95 (big drop)");
        pub.updatePrice(95);

        // Unsubscribe the chart
        pub.unsubscribe(sub1);

        System.out.println("\nUnsubscribed LineChart. Price 95 -> 96");
        pub.updatePrice(96);
    }
}

// Data sent to the panels
record Quote(String symbol, double price, double variation) {}

// Subject (Publisher)
class Asset {
    private final String symbol;
    private double price;

    // Safe list for threads
    private final Set<QuoteSubscriber> subscribers = new CopyOnWriteArraySet<>();

    public Asset(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }

    public void updatePrice(double newPrice) {
        // % variation against the previous price
        double variation = (newPrice - price) / price * 100;
        price = newPrice;
        notifySubscribers(new Quote(symbol, newPrice, variation));
    }

    // Publisher logic/methods
    private void notifySubscribers(Quote quote) {
        for (QuoteSubscriber subscriber : subscribers) {
            try {
                subscriber.update(quote);
            } catch (Exception e) {
                System.err.println("Failed to update: " + subscriber.getClass().getSimpleName() + " | " + e.getMessage());
            }
        }
    }

    public void subscribe(QuoteSubscriber o)   {subscribers.add(o);}
    public void unsubscribe(QuoteSubscriber o) {subscribers.remove(o);}
}


// Contract
interface QuoteSubscriber {
    void update(Quote quote);
}

// Observer (Subscriber)

class LineChart implements QuoteSubscriber {
    @Override
    public void update(Quote quote) {
        System.out.println("[LineChart] New point for " + quote.symbol() + ": " + quote.price());
    }
}

class SummaryTable implements QuoteSubscriber {
    @Override
    public void update(Quote quote) {
        System.out.printf("[SummaryTable] %s | %.2f | %+.2f%%%n", quote.symbol(), quote.price(), quote.variation());
    }
}

// Only reacts when the variation is big enough
class VolatilityAlert implements QuoteSubscriber {
    private final double limit;

    public VolatilityAlert(double limit) {
        this.limit = limit;
    }

    @Override
    public void update(Quote quote) {
        if (Math.abs(quote.variation()) >= limit) {
            System.out.printf("[VolatilityAlert] %s moved %+.2f%%!%n", quote.symbol(), quote.variation());
        }
    }
}
