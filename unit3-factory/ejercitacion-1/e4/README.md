# Ejercicio 4: Fábrica de Exportadores de Reportes Financieros

## Consigna

### Contexto del problema

Un sistema contable genera informes mensuales que los usuarios pueden exportar en formatos variados, principalmente PDF y Hojas de Cálculo CSV, aplicando reglas de formato específicas para cada estructura de archivo.

### Objetivo de aprendizaje

Diseñar un Factory Method que gestione la creación de componentes generadores de reportes aplicando reglas de negocio específicas por formato.

### Requisitos funcionales

- Permitir la generación de reportes estructurados con encabezados y datos tabulares en formato PDF y CSV.
- Asegurar que cada exportador implemente una validación previa de los datos financieros antes de realizar la exportación efectiva.

### Restricciones técnicas

- El sistema creador base debe definir un método plantilla o de fábrica que combine la validación interna con la creación del objeto exportador concreto.
- Las clases de exportación concreta deben encapsular por completo la librería o lógica de formato correspondiente.

## Resolución
```text
$ java e4/FinancialReportExample.java
    Downloading as pdf...
    Concept | Amount
    Sales | 15000
    Expenses | 6200
    Downloading as csv...
    Concept,Amount
    Sales,15000
    Expenses,6200
```
