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

_Pendiente de resolución._
