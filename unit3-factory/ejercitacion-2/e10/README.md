# Ejercicio 10: Sistema Empresarial de Emisión de Títulos de Propiedad

## Consigna

### Contexto del problema

En un sistema notarial automatizado, la emisión de títulos legales debe ajustarse estrictamente a las normativas registrales de diferentes jurisdicciones, requiriendo validaciones de identidad, sellados digitales y formatos documentales regulados por cada entidad gubernamental.

### Objetivo de aprendizaje

Desarrollar un Factory Method robusto de grado empresarial que combine seguridad estricta, validación de reglas legales complejas y protección contra errores de instanciación.

### Requisitos funcionales

- Construir títulos de propiedad electrónicos que incluyan códigos de validación notarial y firmas digitales generadas según las leyes de la jurisdicción emisora.
- Validar la integridad de los datos legales antes de permitir que la factoría devuelva el documento oficial listo para su registro.

### Restricciones técnicas

- La implementación de las factorías y sus productos debe ser completamente segura ante entornos multihilo y resistente a intentos de inyección de parámetros inválidos.
- La estructura debe impedir la creación de documentos legales si los datos obligatorios de la jurisdicción no están presentes en el contexto de la solicitud.

## Resolución

```text
$ java e10/PropertyTitleExample.java              
    PropertyTitle[jurisdiction=AR, fiscalValue=2.5E7, notarialCode=ESC-df8a23fc, signature=df8a23fc]
    PropertyTitle[jurisdiction=MX, fiscalValue=900000.0, notarialCode=FR-eeda6ef4, signature=eeda6ef4]
    Error: Falta el dato obligatorio: matricula
    Error: Falta el dato obligatorio: dni
    Error: El valor fiscal debe ser mayor a 0
```