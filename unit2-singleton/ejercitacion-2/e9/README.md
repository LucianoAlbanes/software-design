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

