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

