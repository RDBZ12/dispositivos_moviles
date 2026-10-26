# Documentación — Directorio de Usuarios

## Objetivo
Desarrollar una aplicación Android que consulte un servicio REST, presente un directorio de usuarios y sus publicaciones, y permita guardar usuarios favoritos de forma local.

## Tecnologías
- Kotlin y Jetpack Compose para la interfaz.
- Retrofit y Gson para consumir JSONPlaceholder.
- Room para guardar favoritos localmente.
- Coroutines y Flow para tareas asíncronas y observación de favoritos.
- GitHub Actions para intentar compilar automáticamente el proyecto.

## Funcionalidades
1. Consultar usuarios y mostrar nombre, correo y empresa.
2. Ver información adicional de un usuario.
3. Consultar las publicaciones relacionadas con un usuario.
4. Añadir o quitar usuarios de favoritos.
5. Conservar los favoritos localmente con Room.
6. Mostrar mensajes de error y opción para reintentar la carga inicial.

## API utilizada
- GET https://jsonplaceholder.typicode.com/users
- GET https://jsonplaceholder.typicode.com/users/{id}
- GET https://jsonplaceholder.typicode.com/posts?userId={id}

JSONPlaceholder es un servicio de prueba; sus datos no son información real.

## Arquitectura
- **Interfaz:** MainActivity.kt, con componentes de Jetpack Compose.
- **Acceso remoto:** DirectoryApi.kt, con endpoints y modelos de respuesta.
- **Persistencia:** DirectoryDatabase.kt, con entidad y DAO de Room.
- **Repositorio:** DirectoryRepository.kt, que conecta la API y los datos locales.
- **Inicialización:** DirectoryApplication.kt, que prepara Retrofit, Room y el repositorio.

## Ejecución
1. Abrir la carpeta DirectorioUsuarios con Android Studio.
2. Configurar JDK 17 y Android SDK 35.
3. Sincronizar Gradle.
4. Ejecutar la aplicación en un emulador o dispositivo con conexión a Internet para cargar usuarios.

## Verificación pendiente
El código se ha publicado en el repositorio, pero todavía debe comprobarse el resultado de GitHub Actions y probarse en un dispositivo. Añadir capturas reales de la aplicación y los datos del estudiante antes de entregar el informe.
