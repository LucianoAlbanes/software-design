package e10;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InvoiceExample {
    public static void main(String[] args) {
        try {
            // Green Test
            InvoiceCreator ar = new ARInvoiceCreator();
            Invoice inv = ar.issue(new InvoiceRequest(
                    Map.of("cuit", "20123456789", "puntoVenta", "3"), 1500.0));
            System.out.println(inv);

            Invoice mx = new MXInvoiceCreator().issue(new InvoiceRequest(
                    Map.of("rfc", "XAXX010101000", "usoCfdi", "G01"), 800.0));
            System.out.println(mx);

            // Red tests
            try {
                ar.issue(new InvoiceRequest(Map.of("cuit", "20123456789"), 1500.0));
            } catch (InvoiceException e) {
                System.out.println("Error: " + e.getMessage());
            }
            try {
                ar.issue(new InvoiceRequest(Map.of("cuit", " ", "puntoVenta", "3"), 1500.0));
            } catch (InvoiceException e) {
                System.out.println("Error: " + e.getMessage());
            }
            new ESInvoiceCreator().issue(new InvoiceRequest(Map.of("nif", "B12345678"), -5.0));

        } catch (InvoiceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


// Excepción

class InvoiceException extends Exception {
    public InvoiceException(String message) {
        super(message);
    }
}

// Pedido: inmutable, copia defensiva de los datos (no se pueden inyectar cambios después)

record InvoiceRequest(Map<String, String> data, double amount) {
    InvoiceRequest {
        data = Map.copyOf(data);
    }
}

// Producto: inmutable, por lo tanto seguro entre hilos

record Invoice(String country, double amount, String authCode, String seal) {}


// Creators: sin estado mutable, seguros entre hilos

abstract class InvoiceCreator {
    // Datos obligatorios de la jurisdicción
    protected abstract List<String> requiredFields();

    protected abstract Invoice createInvoice(InvoiceRequest request);

    // Template Method: valida antes de crear y verifica el resultado antes de devolverlo
    public final Invoice issue(InvoiceRequest request) throws InvoiceException {
        if (request.amount() <= 0) {
            throw new InvoiceException("El monto debe ser mayor a 0");
        }
        for (String field : requiredFields()) {
            String value = request.data().get(field);
            if (value == null || value.isBlank()) {
                throw new InvoiceException("Falta el dato obligatorio: " + field);
            }
        }

        Invoice invoice = createInvoice(request);

        if (invoice.authCode() == null || invoice.seal() == null) {
            throw new InvoiceException("Comprobante incompleto: sin código de autorización o sello");
        }
        return invoice;
    }

    // Sello simulado (no es una firma real): hash de los datos
    protected static String seal(String country, InvoiceRequest request) {
        return Integer.toHexString(Objects.hash(country, request.data(), request.amount()));
    }
}

class ARInvoiceCreator extends InvoiceCreator {
    @Override
    protected List<String> requiredFields() {
        return List.of("cuit", "puntoVenta");
    }

    @Override
    protected Invoice createInvoice(InvoiceRequest request) {
        String seal = seal("AR", request);
        return new Invoice("AR", request.amount(), "CAE-" + seal, seal);
    }
}

class MXInvoiceCreator extends InvoiceCreator {
    @Override
    protected List<String> requiredFields() {
        return List.of("rfc", "usoCfdi");
    }

    @Override
    protected Invoice createInvoice(InvoiceRequest request) {
        String seal = seal("MX", request);
        return new Invoice("MX", request.amount(), "UUID-" + seal, seal);
    }
}

class ESInvoiceCreator extends InvoiceCreator {
    @Override
    protected List<String> requiredFields() {
        return List.of("nif");
    }

    @Override
    protected Invoice createInvoice(InvoiceRequest request) {
        String seal = seal("ES", request);
        return new Invoice("ES", request.amount(), "ES-" + seal, seal);
    }
}
