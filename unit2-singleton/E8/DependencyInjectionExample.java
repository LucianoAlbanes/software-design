package e8;

//ALBANES, Luciano Joaquín

public class DependencyInjectionExample {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService(PaymentGateway.getInstance());
        paymentService.pay(420.69);
    }

}

interface PaymentProcessor {
    void processPayment(double amount);
}


class PaymentGateway implements PaymentProcessor {
    private static final PaymentGateway INSTANCE = new PaymentGateway();
    
    private PaymentGateway() {}

    // Public static methods
    public static PaymentGateway getInstance() {
        return INSTANCE;
    }

    // Public methods
    @Override 
    public void processPayment(double amount) {
        System.out.println("Processing payment: $" + amount);
    }
}


class PaymentService {
    private final PaymentProcessor paymentProcessor;

    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void pay(double amount) {
        paymentProcessor.processPayment(amount);
    }
}

