package e6;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CurrencyQuoteExample {
    public static void main(String[] args) {
        // Create publisher (one place for all currencies)
        CurrencyExchange pub = new CurrencyExchange();

        // Create 3 subscribers and subscribe them
        TrendChart sub1      = new TrendChart();
        ConversionTable sub2 = new ConversionTable();
        LimitAlarm sub3      = new LimitAlarm("USD", 1500); // rings if USD goes over 1500

        pub.subscribe(sub1);
        pub.subscribe(sub2);
        pub.subscribe(sub3);

        System.out.println("First values: USD 1400, EUR 1600");
        pub.updateRate("USD", 1400);
        pub.updateRate("EUR", 1600);

        System.out.println("\nUSD goes up to 1540");
        pub.updateRate("USD", 1540);

        // Unsubscribe the chart
        pub.unsubscribe(sub1);

        System.out.println("\nRemoved TrendChart. EUR goes down to 1580");
        pub.updateRate("EUR", 1580);
    }
}

// Data sent to the panels
record Quote(String currency, double value, double variation) {}

// Subject (Publisher)
class CurrencyExchange {
    private final Map<String, Double> rates = new HashMap<>();

    // Safe list for threads
    private final Set<QuoteSubscriber> subscribers = new CopyOnWriteArraySet<>();

    public void updateRate(String currency, double value) {
        // % variation against the last value (0 the first time)
        Double old = rates.get(currency);
        double variation = (old == null) ? 0 : (value - old) / old * 100;

        rates.put(currency, value);
        notifySubscribers(new Quote(currency, value, variation));
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

class TrendChart implements QuoteSubscriber {
    @Override
    public void update(Quote quote) {
        String trend = (quote.variation() > 0) ? "up" : (quote.variation() < 0) ? "down" : "-";
        System.out.println("[TrendChart] " + quote.currency() + " " + trend);
    }
}

class ConversionTable implements QuoteSubscriber {
    @Override
    public void update(Quote quote) {
        System.out.printf("[ConversionTable] 1 %s = $%.2f ARS (%+.2f%%)%n", quote.currency(), quote.value(), quote.variation());
    }
}

// Only cares about one currency and one limit
class LimitAlarm implements QuoteSubscriber {
    private final String currency;
    private final double limit;

    public LimitAlarm(String currency, double limit) {
        this.currency = currency;
        this.limit = limit;
    }

    @Override
    public void update(Quote quote) {
        if (quote.currency().equals(currency) && quote.value() > limit) {
            System.out.println("[LimitAlarm] " + currency + " over " + limit + "!");
        }
    }
}
