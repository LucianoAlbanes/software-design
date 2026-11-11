# Ejercicio 2: Registro Centralizado de Actividades de la Biblioteca

## Consigna

### Contexto del problema

Los sectores de préstamos, devoluciones y atención a socios registran sus actividades por separado. Esto dificulta reconstruir qué operaciones se realizaron y en qué orden. La biblioteca necesita un registro compartido durante la ejecución de la aplicación.

### Objetivo de aprendizaje

Utilizar Singleton para centralizar la incorporación y consulta de eventos provenientes de distintos módulos.

### Requisitos funcionales

- Registrar actividades con fecha, hora, sector y descripción.
- Consultar el historial completo en el orden de incorporación.
- Filtrar las actividades por sector.
- Mostrar la cantidad total de eventos registrados.

### Restricciones técnicas

- Debe existir una única instancia del registro de actividades.
- Los tres sectores deben incorporar eventos al mismo registro.
- La colección interna debe ser privada y no debe poder modificarse directamente desde las consultas.
- No se permite resolver el ejercicio mediante una clase compuesta únicamente por métodos y atributos estáticos.

## Resolución
```text
$ java e2/LibraryEventLoggerExample.java
    [PRESTAMOS] 2026-11-10T19:16:09.487649Z | Prestamo a Fulano Mingano - B102
    [ATENCION_A_SOCIOS] 2026-11-10T19:16:09.489426Z | Alta Débito Automático - S100
    [PRESTAMOS] 2026-11-10T19:16:09.489454Z | Prestamo a Mengano Zutano - B205
    Are both references the same instance? true
    [[PRESTAMOS] 2026-11-10T19:16:09.487649Z | Prestamo a Fulano Mingano - B102, [ATENCION_A_SOCIOS] 2026-11-10T19:16:09.489426Z | Alta Débito Automático - S100, [PRESTAMOS] 2026-11-10T19:16:09.489454Z | Prestamo a Mengano Zutano - B205]
    [[PRESTAMOS] 2026-11-10T19:16:09.487649Z | Prestamo a Fulano Mingano - B102, [PRESTAMOS] 2026-11-10T19:16:09.489454Z | Prestamo a Mengano Zutano - B205]
    Total events: 3
```
