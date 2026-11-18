# Ejercicio 6: Panel de Cotizaciones Bursátiles en Tiempo Real

## Consigna

### Contexto del problema

Una plataforma financiera procesa el valor cambiante de acciones en el mercado de valores y debe alimentar en tiempo real a diferentes paneles de visualización de los inversores (gráficos de líneas, tablas de resumen y alertas de volatilidad).

### Objetivo de aprendizaje

Manejar la propagación eficiente de datos numéricos frecuentes mediante un punto centralizado de notificación basada en eventos.

### Requisitos funcionales

- Actualizar el precio de un activo financiero y calcular su variación porcentual respecto al valor anterior.
- Enviar el nuevo valor de cotización a todos los paneles de inversores suscritos de manera inmediata.

### Restricciones técnicas

- Los paneles observadores deben recibir el objeto de cotización actualizado o los datos primitivos necesarios para redibujar sus componentes sin sobrecargar la memoria.
- La gestión de la lista de suscriptores debe ser segura ante modificaciones concurrentes durante el envío de datos.

## Resolución

```text
$ java e6/StockQuoteExample.java
    Price 100 -> 102
    [LineChart] New point for YPF: 102.0
    [SummaryTable] YPF | 102.00 | +2.00%

    Price 102 -> 95 (big drop)
    [LineChart] New point for YPF: 95.0
    [SummaryTable] YPF | 95.00 | -6.86%
    [VolatilityAlert] YPF moved -6.86%!

    Unsubscribed LineChart. Price 95 -> 96
    [SummaryTable] YPF | 96.00 | +1.05%
```