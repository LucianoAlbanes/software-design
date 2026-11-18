# Ejercicio 5: Automatización de Persianas según Sensor de Luz Solar

## Consigna

### Contexto del problema

En un sistema domótico inteligente, un sensor fotoeléctrico mide la intensidad de la luz solar exterior y debe ordenar de manera coordinada el cierre automático de las persianas motorizadas y la atenuación de las luces interiores de la vivienda.

### Objetivo de aprendizaje

Controlar la interacción segura en escenarios complejos de múltiples sujetos observables y dispositivos actuadores mediante una arquitectura desacoplada.

### Requisitos funcionales

- Simular la variación de los niveles de luminosidad exterior reportados por el sensor fotoeléctrico.
- Activar de forma simultánea las respuestas mecánicas y eléctricas de los sistemas receptores vinculados al cambio lumínico.

### Restricciones técnicas

- La clase del sensor debe implementar el patrón Observer de manera que múltiples dispositivos actuadores puedan escuchar las variaciones de luz sin acoplamiento rígido.
- La adición de un nuevo dispositivo automatizado no debe requerir modificaciones en el código fuente del sensor.

## Resolución

```text
$ java e5/LightSensorExample.java
    Strong sun on the front (900 lux)...
    [Blinds Living room] closing (900 lux)
    [IndoorLights] Front sensor says 900 lux -> lights at 20%

    Cloudy on the back (200 lux)...
    [Blinds Bedroom] opening (200 lux)
    [IndoorLights] Back sensor says 200 lux -> lights at 80%
```