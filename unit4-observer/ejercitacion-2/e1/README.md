# Ejercicio 1: Notificador de Clic en Botón de Interfaz

## Consigna

### Contexto del problema

Una librería gráfica minimalista necesita que un botón interactivo informe de manera automática a una función de la ventana principal cuando el usuario hace clic sobre él, sin que el botón deba conocer los detalles internos de la ventana.

### Objetivo de aprendizaje

Comprender la estructura básica y el propósito fundamental del patrón Observer mediante la relación de uno a muchos entre un sujeto observable y sus observadores dependientes.

### Requisitos funcionales

- Permitir registrar funciones o manejadores de eventos interesados en las acciones de pulsación del botón.
- Notificar de forma automática a todos los oyentes suscritos cada vez que se simule un evento de clic sobre el componente gráfico.

### Restricciones técnicas

- El sujeto observable (Botón) debe mantener una colección interna genérica de observadores sin acoplarse a implementaciones concretas de ventanas.
- Los observadores deben implementar una interfaz o contrato común que declare el método de recepción del evento de clic.

## Resolución

