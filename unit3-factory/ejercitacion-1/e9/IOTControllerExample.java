package e9;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IOTControllerExample {
    public static void main(String[] args) {
        // Primero, dar de alta los protocolos en ControllerRegister.
        // Y este es el unico punto de acceso a los Controllers.

        ControllerRegistry.register("MQTT", new MQTTControllerCreator());
        ControllerRegistry.register("HTTP", new HTTPControllerCreator());
        ControllerRegistry.register("CoAP", new CoAPControllerCreator());

        
        try {
            // Green Test
            IOTController mqtt = ControllerRegistry.create("MQTT");
            mqtt.connect("mqtt://broker.local:1883", "sensor1", "secreto");

            System.out.println("MQTT conectado: " + mqtt.isConnected());
            mqtt.disconect();
            
            ControllerRegistry.create("HTTP");
            
            // Red test
            ControllerRegistry.create("CoAP").connect("http://mal-esquema", "u", "p");
        
        } catch (IOTException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Nuevo protocolo en runtime, sin tocar el código existente (falla al inicializar)
        ControllerRegistry.register("LoRa", new LoRaControllerCreator());

        
        try {
            ControllerRegistry.create("LoRa");
        } catch (IOTException e) {
            System.out.println("Error: " + e.getMessage());
        }


        // Imprimir métricas
        System.out.println("Creados: " + Metrics.created);
        System.out.println("Fallidos: " + Metrics.failed);
    }
}


// Excepción

class IOTException extends Exception {
    public IOTException(String message) {
        super(message);
    }
}

// Métricas: contadores estáticos, uno por protocolo.
// Un static en la clase base sería uno solo para todas las subclases.

class Metrics {
    static final Map<String, Integer> created = new ConcurrentHashMap<>();
    static final Map<String, Integer> failed = new ConcurrentHashMap<>();
}


// Interfaces

interface IOTController {
    void connect(String url, String user, String pass) throws IOTException;
    void disconect();
    boolean isConnected();
}

// Base: protocolo, estado y validación comunes (Template Method)

abstract class BaseIOTController implements IOTController {
    private final String protocol;
    private boolean connected = false;

    BaseIOTController(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public synchronized void connect(String url, String user, String pass) throws IOTException {
        if (url == null || user == null || pass == null || user.isBlank() || pass.isBlank()) {
            throw new IOTException(protocol + ": url, usuario y contraseña son obligatorios");
        }
        if (!url.startsWith(protocol.toLowerCase() + "://")) {
            throw new IOTException(protocol + ": esquema de URL inválido en " + url);
        }
        // acá iría la conexión real (simulada); MQTT: falta check de liveness
        connected = true;
    }

    @Override
    public synchronized void disconect() {
        connected = false;
    }

    @Override
    public synchronized boolean isConnected() {
        return connected;
    }
}

class MQTTController extends BaseIOTController {
    MQTTController() { super("MQTT"); }
}

class HTTPController extends BaseIOTController {
    HTTPController() { super("HTTP"); }
}

class CoAPController extends BaseIOTController {
    CoAPController() { super("CoAP"); }
}


// Creators

abstract class IOTControllerCreator {
    private final String protocol;

    IOTControllerCreator(String protocol) {
        this.protocol = protocol;
    }

    protected abstract IOTController createIotController() throws IOTException;

    // Template Method: crea y registra la métrica; el cliente no puede saltearla
    public final IOTController create() throws IOTException {
        try {
            IOTController controller = createIotController();
            Metrics.created.merge(protocol, 1, Integer::sum);
            return controller;
        } catch (IOTException e) {
            Metrics.failed.merge(protocol, 1, Integer::sum);
            throw e;
        }
    }
}

class MQTTControllerCreator extends IOTControllerCreator {
    MQTTControllerCreator() { super("MQTT"); }

    @Override
    protected IOTController createIotController() {
        return new MQTTController();
    }
}

class HTTPControllerCreator extends IOTControllerCreator {
    HTTPControllerCreator() { super("HTTP"); }

    @Override
    protected IOTController createIotController() {
        return new HTTPController();
    }
}

class CoAPControllerCreator extends IOTControllerCreator {
    CoAPControllerCreator() { super("CoAP"); }

    @Override
    protected IOTController createIotController() {
        return new CoAPController();
    }
}


// Protocolo agregado en runtime (falla a propósito para ver las métricas de fallo)
class LoRaControllerCreator extends IOTControllerCreator {
    LoRaControllerCreator() { super("LoRa"); }

    @Override
    protected IOTController createIotController() throws IOTException {
        throw new IOTException("Gateway LoRa no disponible");
    }
}


// Registro de creadores en runtime

class ControllerRegistry {
    private static final Map<String, IOTControllerCreator> creators = new ConcurrentHashMap<>();

    static void register(String protocol, IOTControllerCreator creator) {
        creators.put(protocol, creator);
    }

    static IOTController create(String protocol) throws IOTException {
        IOTControllerCreator creator = creators.get(protocol);
        if (creator == null) {
            throw new IOTException("Protocolo no registrado: " + protocol);
        }
        return creator.create();
    }
}
