package e6;

public class PaymentExample {
    public static void main(String[] args) {
        // CREATORS
        PaymentCreator[] creators = {
            new CreditCardPaymentCreator(),
            new BankTransferPaymentCreator(),
            new DigitalWalletPaymentCreator()
        };

        // TEST (With for each, to test inheritance)
        for (PaymentCreator creator : creators) {
            System.out.println(creator.processPayment(500));
        }

        // Amount below the minimum of the bank transfer
        System.out.println(new BankTransferPaymentCreator().processPayment(50));
    }
}


// Interface

interface PaymentMethod {
    double getMinAmount();
    String pay(double amount); // Returns the receipt
}

class CreditCardPayment implements PaymentMethod {
    @Override
    public double getMinAmount() {
        return 10;
    }

    @Override
    public String pay(double amount) {
        double total = amount * 1.05; // 5% fee
        return "Credit card receipt: $" + total;
    }
}

class BankTransferPayment implements PaymentMethod {
    @Override
    public double getMinAmount() {
        return 100;
    }

    @Override
    public String pay(double amount) {
        return "Bank transfer receipt: $" + amount; // No fee
    }
}

class DigitalWalletPayment implements PaymentMethod {
    @Override
    public double getMinAmount() {
        return 1;
    }

    @Override
    public String pay(double amount) {
        double total = amount * 1.02; // 2% fee
        return "Digital wallet receipt: $" + total;
    }
}

// Creators

abstract class PaymentCreator {
    abstract PaymentMethod createPayment();

    // Template method: creates the payment, checks the minimum and charges
    public String processPayment(double amount) {
        PaymentMethod payment = createPayment();
        if (amount < payment.getMinAmount()) {
            return "Amount too low, the minimum is $" + payment.getMinAmount();
        }
        return payment.pay(amount);
    }
}

class CreditCardPaymentCreator extends PaymentCreator {
    @Override
    PaymentMethod createPayment() {
        return new CreditCardPayment();
    }
}

class BankTransferPaymentCreator extends PaymentCreator {
    @Override
    PaymentMethod createPayment() {
        return new BankTransferPayment();
    }
}

class DigitalWalletPaymentCreator extends PaymentCreator {
    @Override
    PaymentMethod createPayment() {
        return new DigitalWalletPayment();
    }
}
