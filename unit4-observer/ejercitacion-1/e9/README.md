# Ejercicio 9: Motor Avanzado de Eventos para Arquitectura de Microservicios Simulada

## Consigna

### Contexto del problema

Una plataforma distribuida simulada requiere un bus de eventos centralizado donde diferentes servicios independientes se registran para escuchar cambios de estado globales (como "UsuarioRegistrado" o "PagoAprobado") y ejecutar acciones asíncronas de manera desacoplada.

### Objetivo de aprendizaje

Diseñar un reto avanzado que combine el patrón Observer con un bus de eventos centralizado, gestión de tipos de mensajes y control eficiente de suscripciones bajo alta concurrencia.

### Requisitos funcionales

- Permitir que los servicios publiquen eventos estructurados conteniendo un identificador de tipo y un payload de datos.
- Despachar el evento publicado exclusivamente a los observadores que se hayan registrado para escuchar esa categoría específica de mensaje.

### Restricciones técnicas

- El bus de eventos debe gestionar diccionarios o listas indexadas por tipo de evento para optimizar la velocidad de enrutamiento.
- La implementación debe ser robusta ante excepciones lanzadas por observadores individuales para evitar que un fallo en un oyente interrumpa la notificación a los demás.

## Resolución

