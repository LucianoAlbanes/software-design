# Ejercicio 10: Sistema Empresarial de Emisión de Comprobantes Fiscales

## Consigna

### Contexto del problema

En un sistema financiero multinacional, la emisión de facturas electrónicas debe ajustarse estrictamente a las normativas tributarias de diferentes países, requiriendo validaciones criptográficas, formatos XML específicos y firmas digitales reguladas por cada jurisdicción.

### Objetivo de aprendizaje

Desarrollar un Factory Method robusto de grado empresarial que combine seguridad estricta, validación de reglas fiscales complejas y protección contra errores de instanciación.

### Requisitos funcionales

- Construir comprobantes fiscales electrónicos que incluyan códigos de autorización y sellos digitales generados según las leyes del país emisor.
- Validar la integridad de los datos tributarios antes de permitir que la factoría devuelva el documento fiscal listo para su transmisión.

### Restricciones técnicas

- La implementación de las factorías y sus productos debe ser completamente segura ante entornos multihilo y resistente a intentos de inyección de configuraciones inválidas.
- La estructura debe impedir la creación de documentos fiscales si los datos obligatorios de la jurisdicción no están presentes en el contexto de la solicitud.

## Resolución

