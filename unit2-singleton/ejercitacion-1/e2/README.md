# Ejercicio 2: Gestor de Conexiones a Base de Datos Simple

## Consigna

### Contexto del problema

En un sistema de software tradicional, abrir y cerrar constantemente conexiones físicas a una base de datos local reduce drásticamente el rendimiento operativo. Se requiere un canal centralizado que controle el acceso al repositorio de datos.

### Objetivo de aprendizaje

Aplicar el patrón Singleton para encapsular un recurso costoso y garantizar un punto único de control de acceso.

### Requisitos funcionales

- Simular la apertura y el establecimiento de una única conexión física a la base de datos.
- Proveer métodos para ejecutar operaciones genéricas de consulta y actualización utilizando siempre la misma conexión activa.

### Restricciones técnicas

- El objeto gestor de conexiones no debe poder duplicarse ni instanciarse libremente desde diferentes módulos del código cliente.
- El estado actual de la conexión debe mantenerse sincronizado a través de la única instancia existente en todo momento.

## Resolución

`DatabaseManager` utiliza un constructor privado y una instancia `static final`, accesible mediante `getInstance()`, para centralizar una conexión simulada con el atributo booleano `connected`. Los métodos `synchronized` protegen la consulta y los cambios de ese estado, además de la ejecución de consultas. `executeQuery()` devuelve un mensaje si la conexión está abierta y lanza `IllegalStateException` si está cerrada. El ejemplo abre la conexión, ejecuta una consulta y luego la cierra desde otra referencia al Singleton, comprobando que ambas comparten el mismo estado y objeto.

```{bash}
java e2/DatabaseManagerExample.java
    Connection: false
    Connection: true
    Consulta Ejecutada correctamente (SELECT * FROM USERS;).
    The DB connection is closed.
    Same instance?: true
```
