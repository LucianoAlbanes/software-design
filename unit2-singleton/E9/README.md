# Ejercicio 9: Sistema de Monitoreo de Métricas de Rendimiento

## Consigna

### Contexto del problema

Una plataforma requiere un colector de métricas de rendimiento (uso de recursos y contadores de operaciones) que opere de manera transversal. Este colector debe recopilar datos de diferentes interceptores de red y componentes distribuidos en la arquitectura de software.

### Objetivo de aprendizaje

Combinar el patrón Singleton con estructuras de agregación de datos y manejo eficiente de memoria bajo alta carga operativa.

### Requisitos funcionales

- Registrar incrementos de contadores de rendimiento y tiempos de latencia desde cualquier punto de la aplicación.
- Proveer un método de exportación que devuelva un resumen consolidado de todas las métricas acumuladas hasta el momento.

### Restricciones técnicas

- El Singleton debe gestionar la sincronización de hilos de forma optimizada, evitando bloqueos excesivos globales.
- La clase debe prevenir la pérdida de datos o desbordamiento de memoria mediante una estrategia interna de control de capacidad o limpieza de métricas cuando alcancen un umbral determinado.

## Resolución

`MetricsCollector` utiliza un Singleton con `ConcurrentHashMap` y `LongAdder` para acumular contadores y latencias de forma concurrente. Solo la creación de métricas nuevas usa `synchronized`, para respetar el límite de 1.000 métricas distintas. Al alcanzarlo, se actualizan las existentes y se contabilizan los registros rechazados en `droppedMetrics`. Las latencias guardan cantidad y tiempo total para calcular el promedio, sin almacenar cada medición. `export()` devuelve un resumen aproximado durante las actualizaciones; el ejemplo espera a que terminen los hilos para mostrar los valores finales.

### Salida de consola

```text
=== PERFORMANCE METRICS ===

Counters:
  http.errors: 100
  http.requests: 100000

Latencies:
  http.request: count=100000, average=19.50 ms

Registered metrics: 3/1000
Dropped metric registrations: 0
```
