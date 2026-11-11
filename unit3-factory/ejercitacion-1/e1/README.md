# Ejercicio 1: Generador Básico de Documentos de Texto

## Consigna

### Contexto del problema

Una aplicación de ofimática necesita crear diferentes tipos de documentos editables (como archivos de texto plano y documentos enriquecidos) sin acoplar el código principal a las clases concretas de cada formato.

### Objetivo de aprendizaje

Comprender la estructura básica y el propósito fundamental del patrón Factory Method delegando la instanciación de objetos en subclases especializadas.

### Requisitos funcionales

- Definir una interfaz común para los documentos que permita invocar una operación de apertura o renderizado básico.
- Permitir la creación de documentos de texto plano y documentos enriquecidos mediante clases creadoras concretas.

### Restricciones técnicas

- El código cliente no debe utilizar operadores de instanciación directa para crear los documentos, sino que debe invocar un método de fábrica abstracto o virtual.
- Las clases creadoras concretas deben retornar el tipo de documento correspondiente a su especialidad.

## Resolución
```text
java e1/DocumentExample.java
I'm a Plain Text Document!
I'm a Rich Text Document!
```
