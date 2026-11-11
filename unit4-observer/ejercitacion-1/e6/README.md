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

