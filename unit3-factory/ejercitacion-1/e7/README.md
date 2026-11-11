# Ejercicio 7: Generador de Elementos de Interfaz Gráfica Adaptativa

## Consigna

### Contexto del problema

Un framework de desarrollo visual necesita renderizar componentes de interfaz (como Botones y Campos de Texto) adaptados visualmente al sistema operativo subyacente (estilo Oscuro de Escritorio o estilo Minimalista Móvil).

### Objetivo de aprendizaje

Implementar un Factory Method que coordine la creación de familias de objetos visuales coherentes bajo un entorno con reglas de diseño específicas.

### Requisitos funcionales

- Crear componentes gráficos interactivos que respondan a eventos de pulsación o escritura según el tema visual activo.
- Asegurar que la factoría construya exclusivamente elementos visuales compatibles entre sí para evitar mezclas de estilos en pantalla.

### Restricciones técnicas

- La estructura creadora debe garantizar que los componentes generados mantengan un contrato estricto de renderizado gráfico.
- Las subclases creadoras deben especializarse en producir componentes con propiedades estéticas homogéneas.

## Resolución

```text
$ java e7/UIElementsExample.java

    [Desktop Oscuro Button]
    Click en DesktopButton
    [Desktop Oscuro TextField] Hello!

    [Mobile Minimalista Button]
    Click en MobileButton
    [Mobile Minimalista TextField] Hello!
```
