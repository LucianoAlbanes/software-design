# Ejercicio 2: Creador de Conexiones a Motores de Base de Datos

## Consigna

### Contexto del problema

Un software de administración de bases de datos debe conectarse opcionalmente a diferentes motores (como SQLite o PostgreSQL) dependiendo de los parámetros configurados por el usuario, requiriendo un punto de creación unificado.

### Objetivo de aprendizaje

Aplicar el patrón Factory Method para encapsular la lógica de selección y construcción de objetos con interfaces similares pero comportamientos subyacentes distintos.

### Requisitos funcionales

- Proveer una interfaz de conexión genérica que declare métodos para ejecutar consultas de prueba.
- Implementar creadores específicos para cada motor de base de datos que devuelvan su respectivo conector configurado.

### Restricciones técnicas

- La lógica que decide qué creador instanciar debe estar separada de la lógica de negocio que utiliza la conexión.
- Cada creador concreto debe sobrescribir el método de fábrica de la clase base para retornar la conexión adecuada.

## Resolución

```text
$ java e2/DatabaseExample.java
    SQLite query: SELECT 1;
    PostregSQL query: SELECT 2;
```