# Ejercicio 5: Administrador de Dispositivos de Hardware Únicos

## Consigna

### Contexto del problema

En un sistema de automatización industrial, el software interactúa con un puerto serial físico único conectado a un dispositivo robótico. Dos componentes enviando comandos simultáneos al mismo puerto provocarían fallas críticas de hardware.

### Objetivo de aprendizaje

Controlar el acceso seguro a recursos físicos exclusivos de un sistema mediante restricciones arquitectónicas estrictas.

### Requisitos funcionales

- Simular el envío de comandos de control al dispositivo físico y la lectura de su estado operacional actual.
- Permitir validar si el dispositivo se encuentra disponible antes de procesar una nueva instrucción.

### Restricciones técnicas

- La clase que representa al controlador de hardware debe impedir estrictamente cualquier intento de duplicación de su instancia en memoria.
- Debe garantizarse que cualquier módulo del sistema obtenga exactamente la misma referencia operativa del controlador.

## Resolución

```{bash}
E5 git:(unit2-singleton) ✗ java Main.java
    Executed: bla
    true
```
