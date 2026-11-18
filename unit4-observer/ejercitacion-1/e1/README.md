# Ejercicio 1: Notificador de Cambio de Temperatura

## Consigna

### Contexto del problema

Una estación meteorológica digital necesita actualizar simultáneamente múltiples pantallas informativas distribuidas en el edificio cada vez que la temperatura ambiente experimenta una variación significativa.

### Objetivo de aprendizaje

Comprender la estructura básica y el propósito fundamental del patrón Observer mediante la relación de uno a muchos entre un sujeto observable y sus observadores dependientes.

### Requisitos funcionales

- Permitir registrar y desvincular pantallas u otros dispositivos interesados en recibir los cambios meteorológicos.
- Notificar automáticamente a todos los dispositivos suscritos cada vez que se actualice el valor de la temperatura en el sensor principal.

### Restricciones técnicas

- El sujeto observable debe mantener una colección interna genérica de observadores sin conocer los detalles de implementación de cada dispositivo visual.
- Los observadores deben implementar una interfaz común que declare el método de recepción de la actualización.

## Resolución

```text
$ java e1/TempNotificatorExample.java
    Subcribed all 3 screens and updated temp to 42.5...
    Sub1 temp: 42.5
    Sub2 temp: 42.5
    Sub3 temp: 42.5
    Removed sub1 and updated temp to 35.
    Sub1 temp: 42.5
    Sub2 temp: 35.0
    Sub3 temp: 35.0
```