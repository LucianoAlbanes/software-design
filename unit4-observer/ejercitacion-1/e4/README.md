# Ejercicio 4: Sistema de Alertas de Stock Bajo para Comercio Electrónico

## Consigna

### Contexto del problema

Una tienda en línea necesita notificar automáticamente al departamento de compras y al sistema de marketing cuando el inventario de un producto desciende por debajo de un umbral mínimo preestablecido, permitiendo activar órdenes de reabastecimiento o campañas de liquidación.

### Objetivo de aprendizaje

Diseñar un Observer funcional que gestione un estado interno dinámico con reglas de negocio condicionales y notificaciones selectivas.

### Requisitos funcionales

- Permitir disminuir la cantidad en stock de un producto a través de operaciones de venta simuladas.
- Evaluar el umbral crítico tras cada modificación y notificar exclusivamente a los departamentos interesados cuando se cumpla la condición de stock bajo.

### Restricciones técnicas

- El producto sujeto debe encapsular los datos de inventario y protegerlos contra modificaciones externas no autorizadas.
- La implementación debe permitir que los observadores decidan qué acción tomar basándose en los datos recibidos durante la notificación.

## Resolución

