# Directorio de Usuarios — Android

Proyecto académico para la asignatura Dispositivos Móviles. Aplicación Android desarrollada con Kotlin y Jetpack Compose para consultar usuarios de JSONPlaceholder, ver detalles y publicaciones, y guardar favoritos localmente con Room.

## Código fuente
El proyecto Android está en la carpeta [DirectorioUsuarios](DirectorioUsuarios/). La documentación del proyecto está en [Documentacion_DirectorioUsuarios.md](DirectorioUsuarios/Documentacion_DirectorioUsuarios.md).

## Cómo abrir la aplicación
1. Descarga o clona este repositorio.
2. Abre la carpeta DirectorioUsuarios desde Android Studio.
3. Usa JDK 17 y Android SDK 35; espera la sincronización de Gradle.
4. Ejecuta la configuración app en un emulador o dispositivo Android.

## Funcionalidades
- Listado de usuarios desde JSONPlaceholder.
- Detalle de usuario y sus publicaciones.
- Favoritos guardados localmente con Room.
- Estados de carga y error con opción para reintentar.
- Flujo de compilación automatizada en GitHub Actions.

## Verificación
La compilación automática se ha configurado, pero debe revisarse el resultado en la pestaña Actions y probarse la app en un emulador o dispositivo antes de considerar la entrega validada. JSONPlaceholder es una API de prueba y sus datos son ficticios.
