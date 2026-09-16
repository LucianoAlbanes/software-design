# Ejercicio 7: Cola de Impresión Centralizada

## Consigna

### Contexto del problema

Una oficina comparte una impresora de red de alto volumen. Múltiples terminales de usuario envían documentos a imprimir de manera concurrente. El sistema operativo de la central de impresión debe procesar los trabajos en orden estricto de llegada sin perder documentos ni duplicar tareas.

### Objetivo de aprendizaje

Implementar un Singleton que coordine una estructura de datos tipo cola bajo un entorno de múltiples peticiones concurrentes.

### Requisitos funcionales

- Permitir encolar nuevos documentos con metadatos asociados (nombre del archivo y autor).
- Procesar secuencialmente los documentos de la cola simulando la impresión física y eliminándolos conforme se completan.

### Restricciones técnicas

- La estructura de la cola y los métodos de encolado y desencolado deben residir exclusivamente dentro de la instancia Singleton.
- La implementación debe contemplar mecanismos de seguridad ante concurrencia para evitar condiciones de carrera al manipular la estructura de datos.

## Resolución

```{bash}
java e7/PrinterQueueExample.java
    Printed: 
            [Luciano]:Hello World!
    Printed: 
            [Lucas]:Hola Mundo!
    Printed: 
            [Tomohiko Itō.]:Kon'nichiwa sekai!
```