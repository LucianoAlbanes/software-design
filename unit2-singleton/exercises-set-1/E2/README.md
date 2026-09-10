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

Se simuló una conexión a una base de datos, mediante un atributo booleano `connected` que nos indica si la conexión está establecida o no.

```{bash}
E2 git:(unit2-singleton) ✗ java Main.java
    Connection: false
    Connection: true
    Consulta Ejecutada correctamente (SELECT * FROM USERS;).
    The DB connection is closed.
    Same instance?: true
```