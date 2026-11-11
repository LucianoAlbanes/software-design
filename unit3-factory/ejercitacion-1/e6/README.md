# Ejercicio 6: Factoría de Métodos de Pago para Comercio Electrónico

## Consigna

### Contexto del problema

Una plataforma de comercio electrónico procesa transacciones monetarias mediante diferentes pasarelas de pago (como Tarjeta de Crédito, Transferencia Bancaria y Monedero Digital), cada una con sus propias reglas de validación y comisiones.

### Objetivo de aprendizaje

Manejar la propagación y creación dinámica de servicios de procesamiento financiero mediante un punto centralizado y polimórfico.

### Requisitos funcionales

- Procesar pagos aplicando las reglas de recargo o validación particulares de cada medio seleccionado por el cliente.
- Permitir que el sistema elija y construya la pasarela de pago adecuada basándose en los datos de la orden de compra.

### Restricciones técnicas

- Las pasarelas de pago concretas deben implementar una interfaz común de cobro que devuelva un comprobante de transacción.
- La factoría encargada de instanciar el método de pago debe validar que el monto cumpla con los requisitos mínimos de la pasarela seleccionada.

## Resolución

