package e9;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SensorChannelExample {
    public static void main(String[] args) {
        // Primero, dar de alta los protocolos en SensorChannelRegistry.
        // Y este es el unico punto de acceso a los canales.

        SensorChannelRegistry.register("ModBus", new ModBusChannelCreator());
        SensorChannelRegistry.register("I2C", new I2CChannelCreator());
        SensorChannelRegistry.register("SPI", new SPIChannelCreator());

        try {
            // Green Test
            SensorChannel modbus = SensorChannelRegistry.create("ModBus");
            modbus.open("/dev/ttyUSB0", 0x0100);

            System.out.println("ModBus abierto: " + modbus.isOpen());
            System.out.println("Lectura: " + modbus.read());
            modbus.close();

            SensorChannelRegistry.create("I2C");

            // Red test
            SensorChannelRegistry.create("SPI").open("/dev/spidev0.0", 0x1FF);

        } catch (HardwareException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Nuevo protocolo en runtime, sin tocar el código existente (falla al inicializar)
        SensorChannelRegistry.register("1-Wire", new OneWireChannelCreator());

        try {
            SensorChannelRegistry.create("1-Wire");
        } catch (HardwareException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Imprimir métricas
        System.out.println("Creados: " + Metrics.created);
        System.out.println("Fallidos: " + Metrics.failed);
    }
}


// Excepción

class HardwareException extends Exception {
    public HardwareException(String message) {
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

interface SensorChannel {
    void open(String port, int address) throws HardwareException;
    double read() throws HardwareException;
    void close();
    boolean isOpen();
}

// Base: protocolo, estado y validación comunes

abstract class BaseSensorChannel implements SensorChannel {
    private final String protocol;
    private final int maxAddress;
    private boolean open = false;

    BaseSensorChannel(String protocol, int maxAddress) {
        this.protocol = protocol;
        this.maxAddress = maxAddress;
    }

    @Override
    public synchronized void open(String port, int address) throws HardwareException {
        if (port == null || !port.startsWith("/dev/")) {
            throw new HardwareException(protocol + ": puerto de hardware inválido: " + port);
        }
        if (address < 0 || address > maxAddress) {
            throw new HardwareException(protocol + ": dirección 0x" + Integer.toHexString(address)
                    + " fuera de rango (máx 0x" + Integer.toHexString(maxAddress) + ")");
        }
        // acá iría la apertura real del puerto (simulada)
        open = true;
    }

    @Override
    public synchronized double read() throws HardwareException {
        if (!open) {
            throw new HardwareException(protocol + ": el canal no está abierto");
        }
        return 21.5; // lectura simulada
    }

    @Override
    public synchronized void close() {
        open = false;
    }

    @Override
    public synchronized boolean isOpen() {
        return open;
    }
}

class ModBusChannel extends BaseSensorChannel {
    ModBusChannel() { super("ModBus", 0xFFFF); }
}

class I2CChannel extends BaseSensorChannel {
    I2CChannel() { super("I2C", 0x7F); }
}

class SPIChannel extends BaseSensorChannel {
    SPIChannel() { super("SPI", 0xFF); }
}


// Creators

abstract class SensorChannelCreator {
    private final String protocol;

    SensorChannelCreator(String protocol) {
        this.protocol = protocol;
    }

    protected abstract SensorChannel createChannel() throws HardwareException;

    // Template Method: crea y registra la métrica; el cliente no puede saltearla
    public final SensorChannel create() throws HardwareException {
        try {
            SensorChannel channel = createChannel();
            Metrics.created.merge(protocol, 1, Integer::sum);
            return channel;
        } catch (HardwareException e) {
            Metrics.failed.merge(protocol, 1, Integer::sum);
            throw e;
        }
    }
}

class ModBusChannelCreator extends SensorChannelCreator {
    ModBusChannelCreator() { super("ModBus"); }

    @Override
    protected SensorChannel createChannel() {
        return new ModBusChannel();
    }
}

class I2CChannelCreator extends SensorChannelCreator {
    I2CChannelCreator() { super("I2C"); }

    @Override
    protected SensorChannel createChannel() {
        return new I2CChannel();
    }
}

class SPIChannelCreator extends SensorChannelCreator {
    SPIChannelCreator() { super("SPI"); }

    @Override
    protected SensorChannel createChannel() {
        return new SPIChannel();
    }
}


// Protocolo agregado en runtime (falla a propósito para ver las métricas de fallo)
class OneWireChannelCreator extends SensorChannelCreator {
    OneWireChannelCreator() { super("1-Wire"); }

    @Override
    protected SensorChannel createChannel() throws HardwareException {
        throw new HardwareException("Puerto 1-Wire no disponible");
    }
}


// Registro de creadores en runtime

class SensorChannelRegistry {
    private static final Map<String, SensorChannelCreator> creators = new ConcurrentHashMap<>();

    static void register(String protocol, SensorChannelCreator creator) {
        creators.put(protocol, creator);
    }

    static SensorChannel create(String protocol) throws HardwareException {
        SensorChannelCreator creator = creators.get(protocol);
        if (creator == null) {
            throw new HardwareException("Protocolo no registrado: " + protocol);
        }
        return creator.create();
    }
}
