# Ejercicio 5: Generador de Enemigos para Videojuego de Acción

## Consigna

### Contexto del problema

Un videojuego de disparos en primera persona genera dinámicamente diferentes tipos de enemigos (como Soldados Rasos y Drones de Combate) en función del nivel de dificultad seleccionado por el jugador, cada uno con atributos de resistencia y velocidad propios.

### Objetivo de aprendizaje

Controlar la creación de jerarquías de objetos complejos con configuraciones iniciales diferenciadas mediante factorías especializadas.

### Requisitos funcionales

- Instanciar enemigos con atributos de salud, daño de ataque y velocidad específicos según el tipo seleccionado.
- Proveer un método de fábrica que construya el enemigo y le asigne automáticamente su comportamiento táctico inicial.

### Restricciones técnicas

- La lógica de asignación de atributos iniciales debe residir dentro de las subclases creadoras o de los productos concretos, evitando bloques condicionales en el cliente.
- El código cliente debe solicitar la creación del enemigo únicamente indicando el tipo deseado a la estructura creadora.

## Resolución

```text
$ java e5/EnemyExample.java
Soldier | HP: 100 | DMG: 10 | SPD: 5 | Tactic: Take cover and shoot
Combat Drone | HP: 60 | DMG: 15 | SPD: 12 | Tactic: Fly around the player
```
