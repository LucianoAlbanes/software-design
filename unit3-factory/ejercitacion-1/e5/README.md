# Ejercicio 5: Generador de Personajes para Videojuego de Rol

## Consigna

### Contexto del problema

Un videojuego de rol permite a los jugadores seleccionar diferentes clases de personajes (como Guerreros, Magos y Arqueros), cada uno con estadísticas iniciales, atributos de combate y equipamiento predeterminado característico.

### Objetivo de aprendizaje

Controlar la creación de jerarquías de objetos complejos con configuraciones iniciales diferenciadas mediante factorías especializadas.

### Requisitos funcionales

- Instanciar personajes con atributos de vida, ataque y defensa específicos según la clase seleccionada.
- Proveer un método de fábrica que construya el personaje y le asigne automáticamente su arma inicial correspondiente.

### Restricciones técnicas

- La lógica de asignación de atributos iniciales debe residir dentro de las subclases creadoras o de los productos concretos, evitando bloques condicionales gigantescos en el cliente.
- El código cliente debe solicitar la creación del personaje únicamente indicando el tipo deseado a la estructura creadora.

## Resolución

