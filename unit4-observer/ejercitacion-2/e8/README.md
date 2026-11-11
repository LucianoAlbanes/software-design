# Ejercicio 8: Refactorización de Código Acoplado hacia Bus de Eventos

## Consigna

### Contexto del problema

Una aplicación heredada de gestión de inventarios contiene llamadas directas y rígidas desde la clase de productos hacia el módulo de facturación y el módulo de estadísticas, haciendo que cualquier cambio en los receptores rompa el código principal.

### Objetivo de aprendizaje

Refactorizar un diseño acoplado basado en dependencias directas hacia un patrón Observer basado en interfaces abstractas e inversión de control.

### Requisitos funcionales

- Eliminar las referencias directas y llamadas fijas a servicios específicos dentro de la lógica de modificación del inventario.
- Proveer una estructura basada en eventos donde el producto actúe como sujeto observable y los módulos externos actúen como observadores independientes.

### Restricciones técnicas

- La clase de inventario debe conservar únicamente la responsabilidad de gestionar su propio stock y notificar a su lista abstracta de oyentes.
- Ninguna clase consumidora o de negocio debe conocer los detalles concretos de los módulos que reciben las actualizaciones de stock.

## Resolución

