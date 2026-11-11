# Ejercicio 6: Gestor de Temas y Apariencia Visual

## Consigna

### Contexto del problema

Una aplicación de productividad permite alternar dinámicamente entre el tema visual claro y el tema oscuro. Todos los componentes de la interfaz gráfica distribuidos en diferentes vistas deben actualizar sus colores simultáneamente al cambiar la preferencia del usuario.

### Objetivo de aprendizaje

Manejar la propagación de estados globales de la interfaz mediante un punto centralizado de acceso.

### Requisitos funcionales

- Mantener el estado actual del tema visual de la aplicación (Claro u Oscuro).
- Permitir cambiar el tema activo y consultar su valor desde cualquier vista o componente visual.

### Restricciones técnicas

- La clase gestora de temas debe implementar el patrón Singleton asegurando que el estado visual no se fragmente entre diferentes vistas o ventanas.
- Los atributos de configuración de colores deben ser modificables únicamente a través de métodos controlados de la clase única.

## Resolución

`ThemeManager` conserva el tema visual en un Singleton con constructor privado e instancia `static final`, de modo que todos los consumidores consultan el mismo estado. El enum `Theme` define las opciones `LIGHT` y `DARK`, y el tema inicial es `LIGHT`. Los métodos `setTheme()` y `getActualTheme()` están sincronizados para proteger la modificación y lectura de la preferencia compartida. El ejemplo cambia a oscuro y luego a claro, imprime ambos valores y comprueba que una segunda referencia apunta al mismo gestor.

```{bash}
java e6/ThemeManagerExample.java
    Actual theme?: DARK
    Actual theme?: LIGHT
    Same instance?: true
```
