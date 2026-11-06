# Ejercicio 8: Refactorización de Código Acoplado hacia el Patrón Observer

## Consigna

### Contexto del problema

Una aplicación heredada de gestión de proyectos contiene llamadas directas y rígidas desde la clase de tareas hacia el módulo de notificaciones por correo y el módulo de estadísticas, haciendo que cualquier cambio en los receptores rompa el código principal.

### Objetivo de aprendizaje

Refactorizar un diseño acoplado basado en dependencias directas hacia un patrón Observer basado en interfaces abstractas e inversión de control.

### Requisitos funcionales

- Eliminar las referencias directas y llamadas fijas a servicios específicos dentro de la lógica de cambio de estado de la tarea.
- Proveer una estructura basada en eventos donde la tarea actúe como sujeto observable y los módulos externos actúen como observadores independientes.

### Restricciones técnicas

- La clase tarea debe conservar únicamente la responsabilidad de gestionar su propio estado y notificar a su lista abstracta de oyentes.
- Ninguna clase consumidora o de negocio debe conocer los detalles concretos de los módulos que reciben las actualizaciones.

## Resolución

