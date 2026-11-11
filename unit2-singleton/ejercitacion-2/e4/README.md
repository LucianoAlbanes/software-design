# Ejercicio 4: Administrador Único de Préstamos

## Consigna

### Contexto del problema

Dos mostradores de una biblioteca registran préstamos desde una misma aplicación. Al utilizar administradores independientes, ambos pueden prestar el mismo ejemplar físico a socios diferentes. Se necesita centralizar el estado de los préstamos.

### Objetivo de aprendizaje

Utilizar Singleton para mantener un estado compartido y aplicar reglas de negocio sobre un recurso identificado individualmente.

### Requisitos funcionales

- Registrar préstamos indicando el identificador del ejemplar, el socio y la fecha.
- Impedir el préstamo de un ejemplar que ya se encuentre prestado.
- Registrar devoluciones para habilitar nuevamente el ejemplar.
- Consultar los préstamos activos de un socio.

### Restricciones técnicas

- Ambos mostradores deben utilizar la misma instancia del administrador.
- Cada ejemplar físico debe tener un identificador único, aunque pertenezca al mismo título.
- Las solicitudes se realizarán de forma secuencial dentro del mismo proceso.
- Una devolución de un ejemplar sin préstamo activo debe rechazarse sin modificar el estado.
- Se debe demostrar que un préstamo registrado desde un mostrador impide prestar el mismo ejemplar desde el otro.

## Resolución

