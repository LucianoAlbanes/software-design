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

`SecurityAuditor` centraliza la auditoría mediante un Singleton con constructor privado e instancia `static final`. Los eventos se almacenan en memoria como `record` inmutables con secuencia, marca temporal, sesión, operador y acción. Los métodos `synchronized` protegen el registro y las consultas; si el reloj repite un instante o retrocede, la marca temporal se ajusta para conservar el orden estricto. `getTotalRecords()` consulta la cantidad y `getAuditTrail()` devuelve una copia inmutable de la traza. El ejemplo registra ocho eventos desde cuatro hilos.


### Salida de consola

Ejemplo de una ejecución. Las marcas temporales, los identificadores de sesión
y el orden entre hilos pueden variar; la secuencia y las marcas temporales
mantienen un orden estrictamente creciente.

```text
=== SECURITY AUDIT ===
Total records: 8
AuditEvent[sequence=1, timestamp=2026-09-16T17:44:08.833924Z, sessionId=cf4a2ca3-376d-4203-894c-dc2f385688a2, operatorId=operator-1, action=Transfer approved]
AuditEvent[sequence=2, timestamp=2026-09-16T17:44:08.834053Z, sessionId=cf4a2ca3-376d-4203-894c-dc2f385688a2, operatorId=operator-1, action=Privileges changed]
AuditEvent[sequence=3, timestamp=2026-09-16T17:44:08.834062Z, sessionId=03f18226-a4ca-4561-8fe2-89f207b0557e, operatorId=operator-4, action=Transfer approved]
AuditEvent[sequence=4, timestamp=2026-09-16T17:44:08.834066Z, sessionId=03f18226-a4ca-4561-8fe2-89f207b0557e, operatorId=operator-4, action=Privileges changed]
AuditEvent[sequence=5, timestamp=2026-09-16T17:44:08.834070Z, sessionId=ee9d81b5-7d84-4eaf-8d52-1bca68fc605b, operatorId=operator-2, action=Transfer approved]
AuditEvent[sequence=6, timestamp=2026-09-16T17:44:08.834073Z, sessionId=ee9d81b5-7d84-4eaf-8d52-1bca68fc605b, operatorId=operator-2, action=Privileges changed]
AuditEvent[sequence=7, timestamp=2026-09-16T17:44:08.834076Z, sessionId=ede852fb-f7d1-4eba-afda-e087b0edb5f1, operatorId=operator-3, action=Transfer approved]
AuditEvent[sequence=8, timestamp=2026-09-16T17:44:08.834080Z, sessionId=ede852fb-f7d1-4eba-afda-e087b0edb5f1, operatorId=operator-3, action=Privileges changed]
```
