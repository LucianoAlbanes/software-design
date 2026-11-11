# Ejercicio 10: Sistema Empresarial de Cumplimiento Normativo y Auditoría de Riesgos

## Consigna

### Contexto del problema

En una institución bancaria global, cualquier alteración en el perfil de riesgo de una cuenta corporativa debe activar de manera concurrente y auditada los subsistemas de prevención de lavado de dinero, análisis legal y notificación a organismos regulatorios internacionales.

### Objetivo de aprendizaje

Desarrollar un Observer robusto de grado empresarial que combine seguridad estricta, trazabilidad de eventos críticos y protección contra fallos en cascada durante la notificación.

### Requisitos funcionales

- Registrar y disparar modificaciones en el nivel de riesgo de una cuenta asegurando la ejecución secuencial y controlada de todos los observadores regulatorios obligatorios.
- Generar un registro inmutable de qué entidades fueron notificadas exitosamente y cuáles reportaron incidencias de procesamiento durante la auditoría.

### Restricciones técnicas

- La arquitectura del sujeto observable debe implementar un mecanismo de aislamiento de errores por cada observador para asegurar que la falla de un sistema regulatorio no bloquee la ejecución de los demás.
- La gestión interna de la lista de observadores debe ser completamente segura ante entornos de ejecución multihilo complejos.

## Resolución

