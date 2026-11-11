# Ejercicio 3: Sistema de Notificaciones Multiplataforma Básico

## Consigna

### Contexto del problema

Una plataforma de servicios requiere enviar alertas a sus usuarios a través de diferentes canales (como Correo Electrónico y Mensajes de Texto SMS) utilizando una interfaz unificada de envío.

### Objetivo de aprendizaje

Utilizar el patrón Factory Method para estandarizar la creación de objetos que comparten un contrato común de comportamiento.

### Requisitos funcionales

- Definir un contrato de notificación que reciba un mensaje de texto y ejecute su envío simulado.
- Crear factorías concretas encargadas de producir notificadores por correo y por SMS de forma independiente.

### Restricciones técnicas

- Las clases consumidoras de las notificaciones deben interactuar exclusivamente con la interfaz abstracta del canal de envío.
- La creación de los objetos de notificación debe resolverse exclusivamente a través de los métodos de fábrica correspondientes.

## Resolución
```text
$ java e3/NotificationExample.java
    SMS Sent: Hello from an SMS!
    Email Sent: Hello Internet!
```
