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

`Controller` concentra el acceso al dispositivo simulado en una instancia `static final`, obtenida mediante `getInstance()` y con constructor privado. `sendCommand()` usa `synchronized` para ejecutar un comando por vez, comprueba la disponibilidad y marca el recurso como ocupado antes de llamar a `executeCommand()`. El bloque `finally` restablece el estado disponible incluso si la ejecución falla. `isFree()` consulta ese estado de forma sincronizada. El ejemplo envía un comando, simula su ejecución mediante un mensaje de consola y verifica que el recurso queda libre al terminar.

```{bash}
java e5/HardwareControllerExample.java
    Executed: bla
    true
```
