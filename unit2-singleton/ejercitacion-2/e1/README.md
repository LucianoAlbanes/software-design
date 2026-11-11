# Ejercicio 1: Configuración General de una Biblioteca

## Consigna

### Contexto del problema

Una biblioteca utiliza una aplicación de escritorio para gestionar sus operaciones. Los módulos de préstamos y consultas necesitan conocer el nombre de la institución, la cantidad máxima de libros por socio y el plazo de devolución. Actualmente, cada módulo crea su propia configuración y los valores pueden quedar desactualizados.

### Objetivo de aprendizaje

Implementar un Singleton básico que centralice la configuración y permita compartir una única instancia entre diferentes módulos.

### Requisitos funcionales

- Consultar y modificar el nombre de la biblioteca, el máximo de libros por socio y el plazo de devolución.
- Acceder a la configuración desde los módulos de préstamos y consultas.
- Reflejar en ambos módulos las modificaciones realizadas.

### Restricciones técnicas

- La clase de configuración debe impedir su instanciación directa desde el código cliente.
- Debe proporcionar un método de acceso que retorne siempre la misma instancia.
- Los atributos deben ser privados y rechazar nombres vacíos o valores numéricos menores o iguales a cero.
- Se debe comprobar mediante una comparación de identidad que ambos módulos utilizan el mismo objeto.

## Resolución

```text
$ java e1/LibraryConfigurationExample.java
    Same instances
    ITU's Great Library
```