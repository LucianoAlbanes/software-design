# Ejercicio 6: Configuración General de un Hospital

## Consigna

### Contexto del problema

Un hospital utiliza una aplicación administrativa con módulos de recepción y consultorios. Ambos necesitan consultar el nombre de la institución, el horario de atención y la duración predeterminada de los turnos. Las configuraciones independientes provocan diferencias entre las pantallas.

### Objetivo de aprendizaje

Aplicar Singleton con inicialización diferida para compartir parámetros generales de una aplicación.

### Requisitos funcionales

- Consultar y modificar el nombre del hospital, el horario de apertura y cierre y la duración de los turnos.
- Acceder a los parámetros desde recepción y consultorios.
- Reflejar los cambios inmediatamente en ambos módulos.

### Restricciones técnicas

- La instancia debe crearse únicamente cuando se solicite por primera vez.
- La clase debe impedir su instanciación directa desde los módulos.
- La duración de los turnos debe ser positiva y el horario de cierre posterior al de apertura.
- Se debe demostrar que las consultas posteriores no construyen nuevas instancias.

## Resolución

```text
$ java e6/HospitalConfigurationExample.java
    Instances before first call: 0
    Instances after first call: 1
    Instances after more calls: 1
    Same instance? true
    Office: Hospital Regional San Martín | 07:30-21:00 | 15 min
    Closing time must be after opening time.
    Appointment duration must be greater than 0.
```
