# Ejercicio 9: Fábrica Avanzada de Conectores IoT para Telemetría

## Consigna

### Contexto del problema

Una plataforma industrial inteligente recopila datos de sensores distribuidos que utilizan diversos protocolos de comunicación (MQTT, HTTP, CoAP), requiriendo un sistema desacoplado que gestione el ciclo de vida y la reconexión de los canales.

### Objetivo de aprendizaje

Diseñar un reto avanzado que combine el Factory Method con principios SOLID, gestión de estado de conexiones y manejo eficiente de recursos bajo alta concurrencia.

### Requisitos funcionales

- Inicializar y configurar canales de comunicación IoT específicos validando parámetros de seguridad y credenciales de red.
- Proveer un mecanismo centralizado que cree el conector adecuado y registre métricas de estado operativo inicial para cada protocolo.

### Restricciones técnicas

- Las factorías concretas deben implementar manejo de excepciones robusto ante fallos en la inicialización de los protocolos de red.
- La arquitectura debe permitir registrar nuevas factorías de protocolos en tiempo de ejecución sin modificar el código base de la aplicación.

## Resolución

