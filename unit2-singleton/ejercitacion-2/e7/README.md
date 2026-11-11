# Ejercicio 7: Generador Central de Números de Atención

## Consigna

### Contexto del problema

La recepción de un hospital dispone de tres terminales que entregan números para trámites administrativos. Cada terminal utiliza un contador independiente, por lo que se generan números repetidos y se producen confusiones en la atención.

### Objetivo de aprendizaje

Mantener un contador único mediante Singleton y compartirlo entre diferentes componentes.

### Requisitos funcionales

- Generar números de atención consecutivos a partir de uno.
- Registrar desde qué terminal se solicitó cada número.
- Consultar el último número emitido sin incrementar el contador.
- Mostrar el historial de números entregados.

### Restricciones técnicas

- El contador debe pertenecer a una única instancia Singleton.
- Las terminales no deben poder modificar directamente el contador.
- Las tres terminales deben ejecutarse en el mismo proceso y solicitar números de forma secuencial.
- Se deben simular al menos veinte solicitudes alternadas sin generar números repetidos.

## Resolución

```text
$ java e7/TicketDispenserExample.java
    Last number: 21
    Last number again: 21
    History: [#1 (Terminal 1), #2 (Terminal 2), #3 (Terminal 3), #4 (Terminal 1), #5 (Terminal 2), #6 (Terminal 3), #7 (Terminal 1), #8 (Terminal 2), #9 (Terminal 3), #10 (Terminal 1), #11 (Terminal 2), #12 (Terminal 3), #13 (Terminal 1), #14 (Terminal 2), #15 (Terminal 3), #16 (Terminal 1), #17 (Terminal 2), #18 (Terminal 3), #19 (Terminal 1), #20 (Terminal 2), #21 (Terminal 3)]
    Consecutive and unique? true
```
