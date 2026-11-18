# Ejercicio 10: Sistema Empresarial de Notificación de Riesgos y Cumplimiento Legal

## Consigna

### Contexto del problema

En una plataforma bancaria internacional, cualquier modificación en los perfiles de riesgo de los clientes debe activar de manera concurrente y auditada los subsistemas de cumplimiento legal, prevención de lavado de dinero y generación de reportes regulatorios obligatorios.

### Objetivo de aprendizaje

Desarrollar un Observer robusto de grado empresarial que combine seguridad estricta, trazabilidad de eventos críticos y protección contra fallos en cascada durante la notificación.

### Requisitos funcionales

- Registrar y disparar cambios en el perfil de riesgo de un cliente asegurando la ejecución secuencial y controlada de todos los observadores regulatorios obligatorios.
- Generar un registro inmutable de qué observadores fueron notificados exitosamente y cuáles reportaron incidencias de procesamiento.

### Restricciones técnicas

- La arquitectura del sujeto observable debe implementar un mecanismo de aislamiento de errores (*try-catch* por observador) para asegurar que la falla de un sistema regulatorio no bloquee la ejecución de los demás.
- La gestión interna de la lista de observadores debe ser completamente segura ante entornos de ejecución multihilo complejos.

## Resolución

```text
$ java e10/RiskComplianceExample.java
    Client C-001 changed to MEDIUM
    [LegalCheck] Checking client C-001 (MEDIUM)
    [MoneyCheck] Checking money moves of C-001
    [ReportService] Report done for C-001

    Client C-002 changed to HIGH (ReportService fails)
    [LegalCheck] Checking client C-002 (HIGH)
    [MoneyCheck] Checking money moves of C-002

    Log:
    C-001 | LegalCheck | OK
    C-001 | MoneyCheck | OK
    C-001 | ReportService | OK
    C-002 | LegalCheck | OK
    C-002 | MoneyCheck | OK
    C-002 | ReportService | ERROR: can't make report for HIGH
```