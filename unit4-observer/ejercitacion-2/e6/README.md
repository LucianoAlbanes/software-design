# Ejercicio 6: Panel Financiero de Variación de Divisas

## Consigna

### Contexto del problema

Una plataforma de cambio de moneda procesa la cotización fluctuante del dólar y el euro en tiempo real, alimentando simultáneamente a diferentes herramientas de visualización para los operadores (gráficos de tendencia, tablas de conversión y alarmas de límite).

### Objetivo de aprendizaje

Manejar la propagación eficiente de datos numéricos frecuentes mediante un punto centralizado de notificación basada en eventos.

### Requisitos funcionales

- Actualizar el valor de cotización de una divisa específica y calcular su diferencia porcentual respecto al ciclo anterior.
- Enviar la nueva estructura de datos de cotización a todos los paneles de inversores suscritos de manera inmediata.

### Restricciones técnicas

- Los paneles observadores deben recibir el objeto de cotización actualizado o los parámetros necesarios para redibujar sus interfaces sin sobrecargar los recursos del sistema.
- La gestión de la lista de suscriptores debe prevenir errores ante modificaciones simultáneas durante el proceso de notificación.

## Resolución

