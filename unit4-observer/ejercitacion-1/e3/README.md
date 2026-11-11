# Ejercicio 3: Registro Automático de Auditoría por Consola

## Consigna

### Contexto del problema

Un sistema de gestión empresarial requiere que cualquier operación crítica realizada sobre los registros de empleados (como alta, baja o modificación de datos) sea informada de manera automática a un módulo de auditoría en consola y a un sistema de bitácora en archivo sin acoplar la lógica de negocio principal.

### Objetivo de aprendizaje

Utilizar el patrón Observer para canalizar eventos de negocio hacia múltiples receptores independientes de manera ordenada.

### Requisitos funcionales

- Disparar un evento de auditoría cada vez que un empleado sea modificado en el sistema central.
- Enviar el mensaje de auditoría de manera simultánea al módulo de consola y al módulo de archivos registrados como oyentes.

### Restricciones técnicas

- La lógica de negocio del empleado no debe contener llamadas directas a las clases de escritura en archivo o consola.
- La suscripción y desuscripción de los auditores debe poder realizarse dinámicamente durante la ejecución del programa.

## Resolución

