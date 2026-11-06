# Ejercicio 10: Administrador Concurrente de Turnos de Consultorio

## Consigna

### Contexto del problema

Un hospital permite solicitar turnos desde recepción y desde un puesto de autoservicio. Ambos componentes se simulan dentro de una misma aplicación. Cuando consultan simultáneamente la disponibilidad de un profesional, pueden asignar el mismo horario a dos pacientes.

### Objetivo de aprendizaje

Integrar Singleton, estado compartido y operaciones atómicas para evitar asignaciones duplicadas.

### Requisitos funcionales

- Cargar una agenda de horarios disponibles por profesional y fecha.
- Reservar un turno indicando paciente, profesional, fecha y horario.
- Rechazar solicitudes para horarios ya ocupados.
- Cancelar turnos y liberar el horario correspondiente.
- Consultar la agenda actualizada desde ambos componentes.

### Restricciones técnicas

- Debe existir una única instancia del administrador de turnos, creada de forma segura ante concurrencia.
- La verificación de disponibilidad y la asignación deben ejecutarse como una operación atómica.
- Se debe simular que al menos veinte hilos intentan reservar el mismo turno.
- Solo una de esas solicitudes debe resultar exitosa.
- Cancelar un turno inexistente no debe modificar la agenda.
- Se deben utilizar datos ficticios de pacientes y mantener toda la simulación dentro de un único proceso.

## Resolución

