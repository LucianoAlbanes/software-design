# Ejercicio 3: Registro de Eventos (Logger) Básico

## Consigna

### Contexto del problema

Toda aplicación requiere registrar eventos, advertencias y errores en un archivo de texto centralizado. Si cada clase abriera su propio flujo de escritura hacia el mismo archivo de forma independiente, se producirían bloqueos de concurrencia y corrupción de datos.

### Objetivo de aprendizaje

Utilizar el patrón Singleton para canalizar la escritura concurrente hacia un recurso de salida compartido de manera ordenada.

### Requisitos funcionales

- Ofrecer métodos públicos para registrar mensajes indicando su nivel de severidad (INFO, WARNING, ERROR).
- Consignar cada mensaje añadiendo una marca de tiempo automática antes de volcarlo al almacenamiento o consola.

### Restricciones técnicas

- El mecanismo de escritura debe ser gestionado exclusivamente por una clase con control estricto de su ciclo de vida único.
- El cliente debe invocar la función de registro mediante una interfaz estática o referencia única sin instanciar la clase de manera directa.

## Resolución

```{bash}
    $ java Main.java
[INFO] 2026-09-13T05:10:13.153273Z | System Started. Unit1
[INFO] 2026-09-13T05:10:13.154667Z | System Started. Unit2
Are both references the same instance? true
[[INFO] 2026-09-13T05:10:13.153273Z | System Started. Unit1, [INFO] 2026-09-13T05:10:13.154667Z | System Started. Unit2]
```
