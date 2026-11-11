# Ejercicio 4: Fábrica de Generadores de Facturas Comerciales

## Consigna

### Contexto del problema

Un sistema de ventas en línea emite comprobantes de pago en distintos formatos estructurados (como Factura Estándar PDF y Ticket Simplificado de Caja) aplicando reglas de formato y cálculo de impuestos específicas para cada estructura.

### Objetivo de aprendizaje

Diseñar un Factory Method que gestione la creación de componentes generadores de documentos aplicando reglas de negocio específicas por formato.

### Requisitos funcionales

- Permitir la generación de comprobantes detallando importes y subtotales en formato PDF y formato de Ticket.
- Asegurar que cada generador implemente una validación previa de los datos monetarios antes de emitir el comprobante.

### Restricciones técnicas

- El sistema creador base debe definir un método de fábrica que combine la validación interna con la creación del objeto generador concreto.
- Las clases de generación concreta deben encapsular por completo la lógica de formato correspondiente.

## Resolución

```text
$ java e4/InvoiceExample.java
PDF Invoice
Subtotal: $1330.5
VAT (21%): $279.405
Total: $1609.905
Ticket | Total: $1330.5
Invalid amounts, can't issue the invoice
```
