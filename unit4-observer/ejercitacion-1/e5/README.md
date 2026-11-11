# Ejercicio 5: Monitor de Sensores de Humos en Edificio Inteligente

## Consigna

### Contexto del problema

En un sistema de automatización de seguridad contra incendios, múltiples sensores inalámbricos ubicados en distintas plantas del edificio actúan como fuentes de eventos que deben alertar simultáneamente a la central de alarmas, al sistema de rociadores automáticos y al servicio de conserjería.

### Objetivo de aprendizaje

Controlar la interacción segura en escenarios complejos de múltiples sujetos observables y observadores mediante una arquitectura desacoplada.

### Requisitos funcionales

- Simular la detección de humo en un sensor específico, enviando una señal de emergencia con la ubicación exacta.
- Activar de forma coordinada las respuestas físicas de los sistemas receptores vinculados a dicha zona.

### Restricciones técnicas

- La clase del sensor debe implementar el patrón Observer de manera que múltiples sistemas de respuesta puedan escuchar una o varias fuentes de peligro simultáneamente.
- Debe garantizarse que la adición de un nuevo tipo de dispositivo de respuesta no requiera modificaciones en el código de los sensores.

## Resolución

