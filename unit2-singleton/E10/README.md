# Ejercicio 10: Contenedor de Estado Global y Auditoría de Seguridad

## Consigna

### Contexto del problema

En una aplicación de alta seguridad, todas las acciones críticas realizadas por operadores (como transferencias o modificaciones de privilegios) deben pasar por un auditor central de seguridad que registre la traza inmutable de auditoría con identificadores de sesión y marcas temporales.

### Objetivo de aprendizaje

Diseñar un Singleton robusto de grado empresarial que combine seguridad contra alteraciones, consistencia de estado y trazabilidad estricta.

### Requisitos funcionales

- Registrar eventos de auditoría asegurando que guarden un orden cronológico estricto e inalterable.
- Permitir la validación de la cantidad total de registros almacenados y la consulta de la traza completa de eventos.

### Restricciones técnicas

- La implementación del Singleton debe ser resistente a intentos de duplicación mediante mecanismos avanzados de protección de instancias.
- La gestión interna del estado de auditoría debe ser totalmente segura ante entornos de ejecución multihilo avanzados.

## Resolución

_Pendiente de resolución._
