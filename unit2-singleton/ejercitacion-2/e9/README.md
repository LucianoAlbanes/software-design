# Ejercicio 9: Cola Compartida de Impresión Administrativa

## Consigna

### Contexto del problema

Los sectores de recepción, facturación y archivo de un hospital utilizan una impresora administrativa compartida. Cuando envían documentos simultáneamente, la creación de gestores independientes provoca pérdidas o duplicaciones de trabajos.

### Objetivo de aprendizaje

Diferenciar la creación segura de un Singleton de la sincronización necesaria para operar sobre su estado.

### Requisitos funcionales

- Encolar documentos con identificador único, nombre, sector y cantidad de páginas.
- Recibir solicitudes desde varios hilos.
- Procesar los documentos en el orden en que fueron incorporados a la cola.
- Emitir un informe de los trabajos procesados.

### Restricciones técnicas

- Todos los hilos deben obtener la misma instancia del gestor de impresión.
- Tanto la creación de la instancia como las operaciones sobre la cola deben ser seguras ante concurrencia.
- Se deben enviar al menos treinta documentos desde diez hilos.
- Cada documento debe procesarse exactamente una vez.
- La impresión debe simularse mediante mensajes, sin requerir una impresora real.

## Resolución

```text
$ java e9/PrintQueueExample.java
    Same instance in all threads? true
    Printing: DOC-1 Form DOC-1 [Recepcion] 1p
    Printing: DOC-2 Form DOC-2 [Recepcion] 2p
    Printing: DOC-3 Form DOC-3 [Recepcion] 3p
    Printing: DOC-10 Form DOC-10 [Recepcion] 1p
    Printing: DOC-4 Form DOC-4 [Facturacion] 1p
    Printing: DOC-11 Form DOC-11 [Recepcion] 2p
    Printing: DOC-7 Form DOC-7 [Archivo] 1p
    Printing: DOC-5 Form DOC-5 [Facturacion] 2p
    Printing: DOC-12 Form DOC-12 [Recepcion] 3p
    Printing: DOC-8 Form DOC-8 [Archivo] 2p
    Printing: DOC-6 Form DOC-6 [Facturacion] 3p
    Printing: DOC-9 Form DOC-9 [Archivo] 3p
    Printing: DOC-13 Form DOC-13 [Facturacion] 1p
    Printing: DOC-14 Form DOC-14 [Facturacion] 2p
    Printing: DOC-15 Form DOC-15 [Facturacion] 3p
    Printing: DOC-16 Form DOC-16 [Archivo] 1p
    Printing: DOC-17 Form DOC-17 [Archivo] 2p
    Printing: DOC-18 Form DOC-18 [Archivo] 3p
    Printing: DOC-22 Form DOC-22 [Facturacion] 1p
    Printing: DOC-23 Form DOC-23 [Facturacion] 2p
    Printing: DOC-24 Form DOC-24 [Facturacion] 3p
    Printing: DOC-19 Form DOC-19 [Recepcion] 1p
    Printing: DOC-20 Form DOC-20 [Recepcion] 2p
    Printing: DOC-21 Form DOC-21 [Recepcion] 3p
    Printing: DOC-25 Form DOC-25 [Archivo] 1p
    Printing: DOC-26 Form DOC-26 [Archivo] 2p
    Printing: DOC-27 Form DOC-27 [Archivo] 3p
    Printing: DOC-28 Form DOC-28 [Recepcion] 1p
    Printing: DOC-29 Form DOC-29 [Recepcion] 2p
    Printing: DOC-30 Form DOC-30 [Recepcion] 3p
    Processed: 30 documents, 60 pages. Pending: 0
    Unique processed ids: 30
    Processed: 30 documents, 60 pages. Pending: 0
```
