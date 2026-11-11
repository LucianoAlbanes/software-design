# Ejercicio 1: Generador Básico de Perfiles de Usuario

## Consigna

### Contexto del problema

Una red social universitaria necesita crear diferentes tipos de perfiles de usuario (como Estudiante y Docente) al momento del registro, asegurando que la lógica de creación esté encapsulada y no dependa directamente del código principal de la interfaz de registro.

### Objetivo de aprendizaje

Comprender la estructura básica y el propósito fundamental del patrón Factory Method delegando la instanciación de objetos en subclases especializadas.

### Requisitos funcionales

- Definir una interfaz común para los perfiles que permita consultar el rol y los permisos básicos asignados.
- Permitir la creación de perfiles de estudiante y perfiles de docente mediante clases creadoras concretas.

### Restricciones técnicas

- El código cliente no debe utilizar operadores de instanciación directa para crear los perfiles, sino que debe invocar un método de fábrica abstracto o virtual.
- Las clases creadoras concretas deben retornar el tipo de perfil correspondiente a su especialidad.

## Resolución

