# Ejercicio 8: Refactorización hacia Inyección de Dependencias

## Consigna

### Contexto del problema

Una aplicación heredada utiliza llamadas estáticas directas a un Singleton de pasarela de pago dispersas por todo el código fuente. Esto hace que el código sea complejo de probar de forma aislada mediante pruebas unitarias con dobles de prueba o simulaciones.

### Objetivo de aprendizaje

Refactorizar un diseño rígido basado en Singleton acoplado hacia un patrón donde la única instancia sea gestionada y provista mediante principios de inversión de dependencias.

### Requisitos funcionales

- Mantener la restricción de que exista una única instancia de la pasarela de pago en todo el ciclo de vida del proceso.
- Permitir que los componentes que consumen el servicio reciban la instancia a través de constructores en lugar de invocar llamadas estáticas rígidas dentro de su lógica interna.

### Restricciones técnicas

- La clase de la pasarela de pago debe conservar su control interno de instancia única, pero debe diseñarse para interoperar correctamente con esquemas de diseño limpios.
- Ninguna clase consumidora debe instanciar directamente la pasarela ni depender de llamadas globales acopladas en su interior.

## Resolución

```{bash}
java e8/DependencyInjectionExample 
    Processing payment: $420.69
```