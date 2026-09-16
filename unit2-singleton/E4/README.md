# Ejercicio 4: Servicio de Caché en Memoria

## Consigna

### Contexto del problema

Una plataforma web necesita almacenar perfiles de usuario consultados frecuentemente en una estructura de caché en memoria para evitar consultas repetitivas al servidor principal. La memoria disponible es limitada, por lo que toda la aplicación debe compartir un único repositorio de caché sincronizado.

### Objetivo de aprendizaje

Diseñar un Singleton funcional que gestione un estado interno dinámico accesible de forma transversal.

### Requisitos funcionales

- Permitir almacenar, recuperar y eliminar elementos de la caché en memoria mediante claves únicas.
- Incluir un mecanismo interno de validación para verificar la existencia de claves y actualizar datos almacenados.

### Restricciones técnicas

- La estructura de datos interna que almacena la caché debe estar encapsulada dentro de la clase Singleton y protegida contra modificaciones externas no autorizadas.
- La implementación debe asegurar que la inicialización de la caché ocurra de forma diferida solo cuando se solicite por primera vez.

## Resolución

`UserCache` implementa un Singleton de inicialización diferida mediante una clase interna `Holder`: la instancia se crea cuando se llama por primera vez a `getInstance()`. La caché almacena pares clave-valor en un `HashMap` privado y ofrece métodos para insertar o actualizar (`put()`), consultar (`get()`), eliminar (`remove()`) y verificar la existencia de una clave (`containsKey()`). Los métodos de inserción y eliminación devuelven el valor anterior o eliminado, o `null` si la clave no existía. El ejemplo agrega un usuario, lo consulta y lo elimina. La inicialización del Singleton es segura entre hilos, aunque las operaciones sobre el `HashMap` no están sincronizadas.

```{bash}
java e4/UserCacheExample.java
    Add new user: null
    username: luciano
    Remove User: luciano
    Key still exists?: false
```
