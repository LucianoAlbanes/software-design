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

