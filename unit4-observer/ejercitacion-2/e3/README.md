# Ejercicio 3: Marcador de Puntuación para Videojuego Sencillo

## Consigna

### Contexto del problema

Un videojuego arcade en consola de texto necesita que la puntuación acumulada del jugador sea mostrada simultáneamente en la pantalla principal de juego y en un registro histórico de récords al finalizar cada nivel.

### Objetivo de aprendizaje

Utilizar el patrón Observer para canalizar eventos de cambio de puntuación hacia múltiples receptores independientes de manera ordenada.

### Requisitos funcionales

- Disparar una actualización cada vez que el jugador sume puntos al destruir un obstáculo o recolectar un objeto.
- Enviar el valor numérico actualizado de manera simultánea al componente visual de juego y al sistema de récords registrados como oyentes.

### Restricciones técnicas

- La lógica de puntuación del juego no debe contener llamadas directas a las clases de renderizado de pantalla o persistencia de récords.
- La suscripción y desuscripción de los componentes oyentes debe poder realizarse de forma dinámica durante la ejecución.

## Resolución

