# Ejercicio 8: Refactorización de Código Acoplado hacia Factory Method

## Consigna

### Contexto del problema

Una aplicación heredada contiene múltiples bloques condicionales anidados distribuidos en todo el código fuente para instanciar manualmente diferentes tipos de comprensores de archivos (ZIP, TAR, GZ), haciendo el sistema rígido y difícil de mantener.

### Objetivo de aprendizaje

Refactorizar un diseño acoplado basado en estructuras condicionales rígidas hacia un patrón Factory Method basado en polimorfismo e inversión de control.

### Requisitos funcionales

- Eliminar las estructuras condicionales de instanciación directa en la lógica de negocio principal.
- Proveer una jerarquía de clases creadoras que resuelvan la selección y construcción del comprensor adecuado de forma transparente.

### Restricciones técnicas

- Las clases consumidoras de los servicios de compresión deben recibir las factorías o los productos mediante interfaces limpias, sin conocer los nombres de las clases concretas.
- La solución debe cumplir estrictamente con el principio de responsabilidad única separando la lógica de selección de la lógica de compresión.

## Resolución

```text
$ java e8/CompressionExamples.java
    Compressed ZIP Comprimiendome
    Expanded ZIP Expandiendome
    Compressed TAR Comprimiendome
    Expanded TAR Expandiendome
    Compressed GZ Comprimiendome
    Expanded GZ Expandiendome
```
