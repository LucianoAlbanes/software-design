# Ejercicio 7: Sistema de Suscripción a Canales de Noticias Digitales

## Consigna

### Contexto del problema

Un portal de noticias en línea permite a los lectores suscribirse a diferentes categorías temáticas (Tecnología, Deportes, Economía). Cuando un redactor publica un artículo nuevo, solo los usuarios suscritos a esa categoría temática específica deben recibir la notificación en su bandeja de avisos.

### Objetivo de aprendizaje

Implementar un Observer que gestione suscripciones filtradas y selectivas bajo un entorno de múltiples tópicos de interés.

### Requisitos funcionales

- Permitir que los usuarios se suscriban o cancelen su suscripción a categorías de noticias particulares.
- Publicar un nuevo artículo especificando su categoría y asegurar que solo los usuarios registrados en ese tópico reciban el aviso.

### Restricciones técnicas

- El sistema central de publicaciones debe actuar como un intermediario o gestor de eventos filtrados por clave temática.
- Los clientes observadores deben implementar una interfaz unificada que reciba el objeto de la noticia publicada.

## Resolución

```text
$ java e7/NewsChannelsExample.java
    Publishing one article per category...
    [Ana] New TECHNOLOGY article: New M5 chip released
    [Ana] New SPORTS article: Godoy Cruz wins the derby
    [Bruno] New SPORTS article: Godoy Cruz wins the derby
    [Carla] New ECONOMY article: Inflation slows down

    Ana unsubscribed from SPORTS. Publishing another sports article...
    [Bruno] New SPORTS article: Mendoza marathon this Sunday

    Inboxes:
    Ana: [New M5 chip released, Godoy Cruz wins the derby]
    Bruno: [Godoy Cruz wins the derby, Mendoza marathon this Sunday]
    Carla: [Inflation slows down]
```