package e4;

public class InvoiceExample {
    public static void main(String[] args) {
        // CREATORS
        InvoiceCreator[] creators = {
            new PDFInvoiceCreator(),
            new TicketInvoiceCreator()
        };

        double[] prices = {1000, 250.5, 80};

        // TEST (With for each, to test inheritance)
        for (InvoiceCreator creator : creators) {
            creator.issueInvoice(prices);
        }

        // Invalid data: negative price
        new PDFInvoiceCreator().issueInvoice(new double[] {1000, -50});
    }
}


// Interface

interface InvoiceGenerator {
    boolean validate(double[] prices);
    void generate(double[] prices);
}

class PDFInvoice implements InvoiceGenerator {
    @Override
    public boolean validate(double[] prices) {
        // Needs at least one item and no negative prices
        if (prices.length == 0) {
            return false;
        }
        for (double price : prices) {
            if (price < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void generate(double[] prices) {
        double subtotal = 0;
        for (double price : prices) {
            subtotal += price;
        }
        double tax = subtotal * 0.21; // 21% VAT
        System.out.println("PDF Invoice");
        System.out.println("Subtotal: $" + subtotal);
        System.out.println("VAT (21%): $" + tax);
        System.out.println("Total: $" + (subtotal + tax));
    }
}

class TicketInvoice implements InvoiceGenerator {
    @Override
    public boolean validate(double[] prices) {
        // Needs at least one item and every price above zero
        if (prices.length == 0) {
            return false;
        }
        for (double price : prices) {
            if (price <= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void generate(double[] prices) {
        double total = 0;
        for (double price : prices) {
            total += price;
        }
        // The ticket only shows the final total, taxes included
        System.out.println("Ticket | Total: $" + total);
    }
}

// Creators

abstract class InvoiceCreator {
    abstract InvoiceGenerator createGenerator();

    // Template method: creates the generator, validates and issues
    public void issueInvoice(double[] prices) {
        InvoiceGenerator generator = createGenerator();
        if (generator.validate(prices)) {
            generator.generate(prices);
        } else {
            System.out.println("Invalid amounts, can't issue the invoice");
        }
    }
}

class PDFInvoiceCreator extends InvoiceCreator {
    @Override
    InvoiceGenerator createGenerator() {
        return new PDFInvoice();
    }
}

class TicketInvoiceCreator extends InvoiceCreator {
    @Override
    InvoiceGenerator createGenerator() {
        return new TicketInvoice();
    }
}
