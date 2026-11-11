# Ejercicio 3: Catálogo Compartido de Libros

## Consigna

### Contexto del problema

Una biblioteca dispone de dos pantallas: una para el personal y otra para consultar el catálogo. Cada pantalla mantiene su propia lista de libros, por lo que los títulos incorporados por el personal no aparecen inmediatamente en la consulta.

### Objetivo de aprendizaje

Administrar una colección compartida mediante una instancia Singleton con operaciones controladas.

### Requisitos funcionales

- Incorporar libros con código único, título, autor y cantidad de ejemplares.
- Buscar libros por título o autor.
- Actualizar la cantidad de ejemplares de un libro.
- Consultar desde ambas pantallas el mismo catálogo actualizado.

### Restricciones técnicas

- El catálogo debe administrarse mediante una única instancia Singleton.
- No se deben aceptar códigos duplicados, títulos vacíos ni cantidades negativas.
- La colección de libros debe permanecer encapsulada.
- Se debe demostrar que un libro agregado desde la pantalla del personal aparece en la pantalla de consulta sin volver a cargar los datos.

## Resolución

```text
$ java e3/BookCatalogExample.java
    Same instance? true
    Query screen: [B101 | Design Patterns - Gamma et al. (2), B102 | Clean Code - Robert C. Martin (3), B103 | Ficciones - Jorge Luis Borges (1)]
    Search 'borges': [B103 | Ficciones - Jorge Luis Borges (1)]
    Search 'design': [B101 | Design Patterns - Gamma et al. (5)]
    Duplicated code: B101
    Code and title can't be blank.
    Copies can't be negative.
```
