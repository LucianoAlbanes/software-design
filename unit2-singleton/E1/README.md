# Ejercicio 1: Configuración Inicial de la Aplicación

## Consignas
Contexto del problema: Una aplicación modular necesita cargar propiedades de configuración desde un archivo maestro al inicio de su ejecución. Si diferentes componentes crearan sus propias instancias del lector de configuración, se desperdiciarían recursos de E/S y se podrían generar valores inconsistentes en memoria.

Objetivo de aprendizaje: Comprender cómo restringir la instanciación de una clase a una única copia global accesible para toda la aplicación.

### Requisitos funcionales:

Permitir cargar parámetros de configuración clave-valor una sola vez al arrancar el sistema.
Proveer un mecanismo de consulta global para que cualquier parte de la aplicación pueda leer un parámetro a partir de su clave.

### Restricciones técnicas:

La clase debe poseer un constructor privado para impedir la creación de objetos mediante el operador de instanciación estándar.

Debe incluir un método estático de acceso público que devuelva siempre la misma referencia única al objeto de configuración.

## Resolución

Se implementó el patrón Singleton en `ConfigurationParameters`, con constructor privado y una instancia `static final` que se devuelve mediante `getInstance()`. Los parámetros de ejemplo se cargan una sola vez al inicializar la clase, simulando la lectura de un archivo mediante un `HashMap`. El constructor guarda una copia inmutable con `Map.copyOf()`, y `getParameter()` permite consultar cada valor por su clave. El ejemplo obtiene dos referencias, verifica que apuntan al mismo objeto y muestra el valor de `hostname`.

```{bash}
java e1/ConfigurationExample.java
Both references point to the same instance.
artemis2
```
