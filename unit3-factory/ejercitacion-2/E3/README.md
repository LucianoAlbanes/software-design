# Ejercicio 3: Sistema de Alertas por Canal de Texto

## Consigna

### Contexto del problema

Un software de monitoreo de servidores requiere enviar mensajes de alerta a los administradores a través de diferentes canales (como Consola del Sistema o Archivo de Registro) utilizando una interfaz unificada de salida.

### Objetivo de aprendizaje

Utilizar el patrón Factory Method para estandarizar la creación de objetos que comparten un contrato común de comportamiento.

### Requisitos funcionales

- Definir un contrato de alerta que reciba un mensaje de texto y ejecute su impresión o almacenamiento.
- Crear factorías concretas encargadas de producir alertadores por consola y por archivo de forma independiente.

### Restricciones técnicas

- Las clases consumidoras de las alertas deben interactuar exclusivamente con la interfaz abstracta del canal de salida.
- La creación de los objetos de alerta debe resolverse exclusivamente a través de los métodos de fábrica correspondientes.

## Resolución

