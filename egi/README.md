# EGI · Despachos de GNC con Observer

Un despacho de la interfaz CORITEC llega por POST, se lee y la central (Subject) lo reparte a los observadores suscriptos: registro de despachos y acumulador de m³ por surtidor. Los repetidos se descartan antes de notificar y si un observador falla, los demás siguen. Usa los 13 POST reales del 3/11/2026 (`capturas/`).

Informe: PDF entregado aparte, en el aula de la materia. No está en este repositorio.

## Dónde está el patrón

| Rol | Clase |
|---|---|
| Subject (interfaz) | `src/egi/observer/DispatchSubject.java` |
| Subject concreto | `src/egi/observer/DispatchCenter.java` |
| Observer (interfaz) | `src/egi/observer/DispatchObserver.java` |
| Observers concretos | `src/egi/observers/` (`DispatchLog`, `PumpTotals`, `FaultyObserver`) |
| Singleton (traza) | `src/egi/Trace.java` |

## Correr

Desde esta carpeta (JDK 17 o más; probado con Temurin 25):

```sh
javac -d out $(find src -name '*.java')
java -cp out egi.ConsoleDemo            # demo completa por consola
java -cp out egi.web.WebServer          # receptor + dashboard en http://127.0.0.1:8090/
java -cp out egi.web.CaptureSender      # otra terminal: manda los 13 POST
java -cp out egi.web.CaptureSender 1 1  # el 1 dos veces: repetido
```

El servidor guarda todo en memoria y comparte el estado entre pestañas: si dos personas tocan el dashboard a la vez, ven lo mismo.

## Derechos

© 2026 Luciano J. Albanes. Todos los derechos reservados. Trabajo hecho sólo para la evaluación de Diseño de Software (ITU, UNCuyo). El código, el informe y las capturas, que son datos reales de una estación, no se pueden copiar, publicar ni redistribuir, ni total ni parcialmente, sin autorización escrita del autor.
