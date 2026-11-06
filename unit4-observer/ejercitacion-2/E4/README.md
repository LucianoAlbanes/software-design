# Ejercicio 4: Sistema de Alertas de Precio en Lista de Deseos

## Consigna

### Contexto del problema

Una tienda en línea permite a los clientes añadir productos a su lista de favoritos. Cuando el precio de un artículo disminuye, el sistema debe notificar de inmediato al usuario mediante un correo electrónico simulado y un aviso en su panel de notificaciones web.

### Objetivo de aprendizaje

Diseñar un Observer funcional que gestione un estado interno dinámico con reglas de negocio condicionales y notificaciones selectivas.

### Requisitos funcionales

- Permitir modificar el precio actual de un producto mediante operaciones de actualización en el catálogo.
- Evaluar si el nuevo precio es inferior al registrado previamente y notificar exclusivamente a los canales vinculados a ese artículo.

### Restricciones técnicas

- El producto observable debe encapsular los datos de valor comercial y protegerlos contra modificaciones externas no autorizadas.
- La implementación debe permitir que los observadores procesen los datos recibidos basándose en el porcentaje de rebaja aplicado.

## Resolución

