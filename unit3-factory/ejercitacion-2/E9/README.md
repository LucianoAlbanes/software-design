# Ejercicio 9: Fábrica Avanzada de Sensores Ambientales para IoT

## Consigna

### Contexto del problema

Una plataforma industrial de telemetría recopila datos de hardware utilizando diversos protocolos de comunicación de bajo nivel (como ModBus, I2C y SPI), requiriendo un sistema desacoplado que gestione la inicialización y lectura de los dispositivos conectados.

### Objetivo de aprendizaje

Diseñar un reto avanzado que combine el Factory Method con principios SOLID, gestión de estado de conexiones y manejo eficiente de recursos bajo alta concurrencia.

### Requisitos funcionales

- Inicializar y configurar canales de lectura de sensores específicos validando parámetros de hardware y direcciones de memoria.
- Proveer un mecanismo centralizado que cree el conector adecuado y registre métricas de estado operativo inicial para cada protocolo.

### Restricciones técnicas

- Las factorías concretas deben implementar manejo de excepciones robusto ante fallos en la inicialización de los puertos de hardware.
- La arquitectura debe permitir registrar nuevas factorías de protocolos en tiempo de ejecución sin modificar el código base de la aplicación.

## Resolución

