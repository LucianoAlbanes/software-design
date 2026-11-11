# Ejercicio 8: Refactorización de Código Condicional hacia Factory Method

## Consigna

### Contexto del problema

Una aplicación heredada contiene múltiples estructuras condicionales anidadas distribuidas en todo el código fuente para instanciar manualmente diferentes tipos de importadores de datos (CSV, XML, JSON), haciendo el sistema rígido y difícil de mantener.

### Objetivo de aprendizaje

Refactorizar un diseño acoplado basado en estructuras condicionales rígidas hacia un patrón Factory Method basado en polimorfismo e inversión de control.

### Requisitos funcionales

- Eliminar las estructuras condicionales de instanciación directa en la lógica de procesamiento principal.
- Proveer una jerarquía de clases creadoras que resuelvan la selección y construcción del importador adecuado de forma transparente.

### Restricciones técnicas

- Las clases consumidoras de los servicios de importación deben recibir las factorías o los productos mediante interfaces limpias, sin conocer los nombres de las clases concretas.
- La solución debe cumplir estrictamente con el principio de responsabilidad única separando la lógica de selección de la lógica de lectura de datos.

## Resolución

```text
$ java e8/ImportExamples.java
    Imported CSV Importandome
    Imported XML Importandome
    Imported JSON Importandome
```
