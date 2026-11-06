# Ejercicio 7: Sistema de Suscripción a Canales de Video Streaming

## Consigna

### Contexto del problema

Una plataforma de contenidos multimedia permite a los usuarios suscribirse a canales de creadores independientes. Cuando un creador sube un nuevo video, solo los usuarios que forman parte de la lista de seguidores de ese canal específico deben recibir una alerta emergente.

### Objetivo de aprendizaje

Implementar un Observer que gestione suscripciones filtradas y selectivas bajo un entorno de múltiples canales independientes.

### Requisitos funcionales

- Permitir que los usuarios se suscriban o cancelen su suscripción a canales de video particulares.
- Publicar un nuevo contenido especificando el identificador del canal y asegurar que solo los usuarios registrados en dicho canal reciban la alerta.

### Restricciones técnicas

- El sistema de canales debe actuar como un gestor de eventos indexados por identificador de emisor.
- Los clientes observadores deben implementar una interfaz unificada que reciba los metadatos del video publicado.

## Resolución

