package e10;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PropertyTitleExample {
    public static void main(String[] args) {
        try {
            // Green Test
            TitleCreator ar = new ARTitleCreator();
            PropertyTitle title = ar.issue(new TitleRequest(
                    Map.of("dni", "30123456", "matricula", "13-04567"), 25000000.0));
            System.out.println(title);

            PropertyTitle mx = new MXTitleCreator().issue(new TitleRequest(
                    Map.of("curp", "GOMC800101HDFRRL09", "folioReal", "12345678"), 900000.0));
            System.out.println(mx);

            // Red tests
            try {
                ar.issue(new TitleRequest(Map.of("dni", "30123456"), 25000000.0));
            } catch (TitleException e) {
                System.out.println("Error: " + e.getMessage());
            }
            try {
                ar.issue(new TitleRequest(Map.of("dni", " ", "matricula", "13-04567"), 25000000.0));
            } catch (TitleException e) {
                System.out.println("Error: " + e.getMessage());
            }
            new ESTitleCreator().issue(new TitleRequest(
                    Map.of("nif", "B12345678", "refCatastral", "1234567VK1234A0001AB"), -5.0));

        } catch (TitleException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


// Excepción

class TitleException extends Exception {
    public TitleException(String message) {
        super(message);
    }
}

// Pedido: inmutable, copia defensiva de los datos (no se pueden inyectar cambios después)

record TitleRequest(Map<String, String> data, double fiscalValue) {
    TitleRequest {
        data = Map.copyOf(data);
    }
}

// Producto: inmutable, por lo tanto seguro entre hilos

record PropertyTitle(String jurisdiction, double fiscalValue, String notarialCode, String signature) {}


// Creators: sin estado mutable, seguros entre hilos

abstract class TitleCreator {
    // Datos obligatorios de la jurisdicción
    protected abstract List<String> requiredFields();

    protected abstract PropertyTitle createTitle(TitleRequest request);

    // Template Method: valida antes de crear y verifica el resultado antes de devolverlo
    public final PropertyTitle issue(TitleRequest request) throws TitleException {
        if (request.fiscalValue() <= 0) {
            throw new TitleException("El valor fiscal debe ser mayor a 0");
        }
        for (String field : requiredFields()) {
            String value = request.data().get(field);
            if (value == null || value.isBlank()) {
                throw new TitleException("Falta el dato obligatorio: " + field);
            }
        }

        PropertyTitle title = createTitle(request);

        if (title.notarialCode() == null || title.signature() == null) {
            throw new TitleException("Título incompleto: sin código notarial o firma");
        }
        return title;
    }

    // Firma simulada (no es una firma real): hash de los datos
    protected static String sign(String jurisdiction, TitleRequest request) {
        return Integer.toHexString(Objects.hash(jurisdiction, request.data(), request.fiscalValue()));
    }
}

class ARTitleCreator extends TitleCreator {
    @Override
    protected List<String> requiredFields() {
        return List.of("dni", "matricula");
    }

    @Override
    protected PropertyTitle createTitle(TitleRequest request) {
        String signature = sign("AR", request);
        return new PropertyTitle("AR", request.fiscalValue(), "ESC-" + signature, signature);
    }
}

class MXTitleCreator extends TitleCreator {
    @Override
    protected List<String> requiredFields() {
        return List.of("curp", "folioReal");
    }

    @Override
    protected PropertyTitle createTitle(TitleRequest request) {
        String signature = sign("MX", request);
        return new PropertyTitle("MX", request.fiscalValue(), "FR-" + signature, signature);
    }
}

class ESTitleCreator extends TitleCreator {
    @Override
    protected List<String> requiredFields() {
        return List.of("nif", "refCatastral");
    }

    @Override
    protected PropertyTitle createTitle(TitleRequest request) {
        String signature = sign("ES", request);
        return new PropertyTitle("ES", request.fiscalValue(), "NOT-" + signature, signature);
    }
}
