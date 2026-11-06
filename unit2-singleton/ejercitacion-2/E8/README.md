# Ejercicio 8: Registro Compartido de Movimientos de Insumos

## Consigna

### Contexto del problema

Los sectores de depósito, consultorios y laboratorio registran por separado los movimientos de insumos. La administración necesita consultar un historial unificado para identificar entradas, salidas y sectores responsables.

### Objetivo de aprendizaje

Centralizar información mediante Singleton y proteger la integridad de un registro compartido.

### Requisitos funcionales

- Registrar movimientos con código de insumo, tipo de movimiento, cantidad, sector y fecha.
- Admitir movimientos de entrada y salida.
- Consultar el historial completo o filtrarlo por insumo y sector.
- Calcular el saldo de cada insumo a partir de sus movimientos.

### Restricciones técnicas

- Debe existir una única instancia del registro durante la ejecución.
- Los tres sectores deben utilizar el mismo objeto.
- Las cantidades deben ser positivas y las salidas no pueden superar el saldo disponible.
- Los movimientos registrados no deben poder editarse ni eliminarse desde el código cliente.
- El saldo inicial de cada insumo debe incorporarse mediante un movimiento de entrada.

## Resolución

