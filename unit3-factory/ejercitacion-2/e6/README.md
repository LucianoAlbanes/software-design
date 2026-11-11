# Ejercicio 6: Factoría de Métodos de Envío Logístico

## Consigna

### Contexto del problema

Una plataforma de comercio electrónico calcula los costos de envío de los pedidos utilizando diferentes transportistas (como Correo Postal Tradicional, Envío Expreso Privado y Retiro en Sucursal), cada uno con sus propias reglas de tarificación y plazos de entrega.

### Objetivo de aprendizaje

Manejar la propagación y creación dinámica de servicios de cálculo logístico mediante un punto centralizado y polimórfico.

### Requisitos funcionales

- Calcular tarifas de envío aplicando las reglas de peso, distancia y recargo particulares de cada transportista seleccionado.
- Permitir que el sistema elija y construya el servicio de transporte adecuado basándose en los datos geográficos de la orden.

### Restricciones técnicas

- Los servicios de transporte concretos deben implementar una interfaz común que devuelva el costo total y el tiempo estimado.
- La factoría encargada de instanciar el método de envío debe validar que los datos de destino cumplan con los requisitos del transportista seleccionado.

## Resolución

```text
$ java e6/ShippingExample.java
PostalShipping: $550.0, 5 days
ExpressShipping: $1340.0, 2 days
PickupShipping: $0.0, 3 days
PickupShipping: invalid destination 'Av. San Martin 123'
```
