# Ejercicio 9: Motor Asíncrono de Eventos para Monitoreo de Servidores

## Consigna

### Contexto del problema

Una infraestructura distribuida simulada requiere un bus de eventos centralizado donde diferentes microservicios se registran para escuchar alertas críticas de sistema (como "CPU al 90%", "Memoria Insuficiente" o "Fallo de Disco") y ejecutar acciones de contingencia de forma desacoplada.

### Objetivo de aprendizaje

Diseñar un reto avanzado que combine el patrón Observer con un bus de eventos centralizado, gestión de tipos de mensajes y control eficiente de suscripciones bajo alta concurrencia.

### Requisitos funcionales

- Permitir que los nodos del sistema publiquen eventos estructurados conteniendo un código de severidad y un cuerpo de datos descriptivo.
- Despachar el evento publicado exclusivamente a los observadores que se hayan registrado para escuchar esa categoría específica de incidencia.

### Restricciones técnicas

- El bus de eventos debe gestionar estructuras de datos indexadas por tipo de alerta para optimizar el enrutamiento de los mensajes.
- La implementación debe ser robusta ante excepciones lanzadas por observadores individuales para evitar que un fallo en un oyente interrumpa la notificación a los demás componentes.

## Resolución

