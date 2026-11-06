# Ejercicio 2: Sincronizador de Interfaz para Reproductor Multimedia

## Consigna

### Contexto del problema

Una aplicación de música muestra el estado de reproducción actual en tres vistas distintas: una barra inferior de control, una ventana flotante de miniatura y un widget en la pantalla principal. Al cambiar de canción o pausar el audio, todas las vistas deben reflejar el cambio al instante.

### Objetivo de aprendizaje

Aplicar el patrón Observer para desacoplar el estado central de un componente multimedia de sus múltiples representaciones visuales en pantalla.

### Requisitos funcionales

- Permitir cambiar el estado de reproducción (reproduciendo, pausado, detenido) y el título de la canción actual.
- Actualizar de manera automática y simultánea a todos los componentes de la interfaz suscritos cuando el estado del reproductor cambie.

### Restricciones técnicas

- Las vistas visuales no deben realizar consultas periódicas (*polling*) al reproductor, sino reaccionar pasivamente a las notificaciones emitidas.
- El motor de reproducción debe encapsular la lógica de gestión de la lista de observadores de forma segura.

## Resolución

