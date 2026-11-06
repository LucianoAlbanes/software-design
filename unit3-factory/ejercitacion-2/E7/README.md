# Ejercicio 7: Generador de Componentes de Interfaz Gráfica Minimalista

## Consigna

### Contexto del problema

Un framework gráfico ligero necesita renderizar componentes de interfaz (como Botones Interactivos y Etiquetas de Texto) adaptados visualmente a diferentes resoluciones de pantalla (modo Escritorio y modo Dispositivo Móvil).

### Objetivo de aprendizaje

Implementar un Factory Method que coordine la creación de familias de objetos visuales coherentes bajo un entorno con reglas de diseño específicas.

### Requisitos funcionales

- Crear componentes gráficos que respondan a eventos de interacción según el modo de pantalla activo.
- Asegurar que la factoría construya exclusivamente elementos visuales compatibles entre sí para mantener la coherencia estética.

### Restricciones técnicas

- La estructura creadora debe garantizar que los componentes generados mantengan un contrato estricto de renderizado.
- Las subclases creadoras deben especializarse en producir componentes con propiedades geométricas homogéneas.

## Resolución

