# Ejercicio 5: Gestor Concurrente de Reservas de una Sala de Lectura

## Consigna

### Contexto del problema

Una biblioteca tiene una sala de lectura con diez lugares. Varios puestos de atención, simulados mediante hilos, reciben solicitudes al mismo tiempo. Si cada puesto controla su propia disponibilidad, se aceptan más reservas que lugares existentes.

### Objetivo de aprendizaje

Implementar un Singleton seguro ante concurrencia y proteger las operaciones sobre cupos compartidos.

### Requisitos funcionales

- Administrar diez lugares para una misma franja horaria.
- Registrar reservas mediante un identificador de socio.
- Rechazar reservas duplicadas o solicitudes cuando no queden lugares.
- Cancelar reservas y recuperar el lugar correspondiente.
- Consultar las reservas activas y los lugares disponibles.

### Restricciones técnicas

- La creación de la instancia Singleton debe ser segura ante solicitudes concurrentes.
- La comprobación de disponibilidad y el registro de la reserva deben constituir una operación atómica.
- Se deben simular al menos treinta solicitudes concurrentes de socios diferentes.
- Las reservas activas nunca deben superar diez y la disponibilidad nunca debe ser negativa.
- Cancelar una reserva inexistente no debe aumentar los lugares disponibles.

## Resolución

```text
$ java e5/ReadingRoomExample.java
    Same instance in all threads? true
    Accepted: 10 | Rejected: 20
    Active: [S100, S103, S104, S102, S101, S105, S106, S107, S108, S109]
    Available seats: 0
    Duplicated S100 accepted? false
    Cancel S100: true
    Available seats: 1
    Cancel S999: false
    Available seats: 1
```
