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

