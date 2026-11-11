# Ejercicio 2: Creador de Conexiones a Repositorios de Archivos

## Consigna

### Contexto del problema

Una aplicación de escritorio permite almacenar archivos multimedia ya sea en el disco local o en un servidor en la nube, requiriendo un punto de creación unificado para los servicios de almacenamiento según la preferencia del usuario.

### Objetivo de aprendizaje

Aplicar el patrón Factory Method para encapsular la lógica de selección y construcción de objetos con interfaces similares pero comportamientos subyacentes distintos.

### Requisitos funcionales

- Proveer una interfaz de almacenamiento genérica que declare métodos para guardar y recuperar archivos.
- Implementar creadores específicos para almacenamiento local y almacenamiento en la nube que devuelvan su respectivo conector configurado.

### Restricciones técnicas

- La lógica que decide qué creador instanciar debe estar separada de la lógica de negocio que gestiona los archivos.
- Cada creador concreto debe sobrescribir el método de fábrica de la clase base para retornar el conector adecuado.

## Resolución

```text
$ java e2/StorageExample.java
Saved on local disk: photo.jpg
Retrieved from local disk: photo.jpg
Uploaded to the cloud: video.mp4
Downloaded from the cloud: video.mp4
```
