# Ejercicio 2: Indicador de Estado de Conexión de Red

## Consigna

### Contexto del problema

Una aplicación de escritorio supervisa el estado de la red informática local y requiere actualizar un icono visual en la barra de herramientas y un mensaje de texto en la barra de estado cada vez que la conexión pasa de activa a inactiva.

### Objetivo de aprendizaje

Aplicar el patrón Observer para desacoplar la fuente de estado de red de sus múltiples representaciones visuales independientes.

### Requisitos funcionales

- Permitir cambiar el estado de la conexión de red (Conectado, Desconectado, Intermitente) mediante un método del monitor.
- Actualizar de manera automática y simultánea al icono visual y al texto de estado cuando la condición de la red cambie.

### Restricciones técnicas

- Los componentes visuales no deben realizar consultas periódicas al monitor de red, sino reaccionar pasivamente a las notificaciones emitidas.
- El monitor observable debe encapsular la gestión de su lista de suscriptores de forma segura.

## Resolución

