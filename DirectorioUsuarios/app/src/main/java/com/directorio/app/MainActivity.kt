package com.directorio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.directorio.app.data.repository.DirectoryRepository
import com.directorio.app.data.repository.Post
import com.directorio.app.data.repository.User
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (application as DirectoryApplication).repository
        setContent { MaterialTheme { DirectoryScreen(repository) } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DirectoryScreen(repository: DirectoryRepository) {
    val scope = rememberCoroutineScope()
    val favorites by repository.favorites().collectAsState(initial = emptyList())
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var selected by remember { mutableStateOf<User?>(null) }
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var showFavorites by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    fun loadUsers() {
        scope.launch {
            loading = true
            error = null
            try { users = repository.users() }
            catch (_: Exception) { error = "No se pudieron cargar los usuarios. Comprueba tu conexión." }
            finally { loading = false }
        }
    }
    LaunchedEffect(Unit) { loadUsers() }

    Scaffold(topBar = {
        TopAppBar(title = { Text(if (selected != null) "Detalle de usuario" else if (showFavorites) "Favoritos" else "Directorio de usuarios") },
            navigationIcon = {
                if (selected != null || showFavorites) {
                    TextButton(onClick = {
                        if (selected != null) selected = null else showFavorites = false
                    }) { Text("Atrás") }
                }
            },
            actions = {
                if (selected == null) {
                    TextButton(onClick = { showFavorites = !showFavorites }) {
                        Text(if (showFavorites) "Usuarios" else "Favoritos (" + favorites.size + ")")
                    }
                }
            })
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(12.dp)) {
            if (selected != null) {
                val user = selected!!
                Text(user.name, style = MaterialTheme.typography.headlineSmall)
                Text(user.email)
                Text("Empresa: " + user.company)
                Text("Ciudad: " + user.city)
                Text("Teléfono: " + user.phone)
                Text("Web: " + user.website)
                Spacer(Modifier.height(12.dp))
                Button(onClick = {
                    scope.launch { repository.toggleFavorite(user) }
                }) { Text(if (favorites.any { it.id == user.id }) "Quitar de favoritos" else "Guardar en favoritos") }
                Spacer(Modifier.height(12.dp))
                Text("Publicaciones", style = MaterialTheme.typography.titleLarge)
                LazyColumn {
                    items(posts, key = { it.id }) { post ->
                        Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(Modifier.padding(12.dp)) {
                                Text(post.title, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(4.dp))
                                Text(post.body)
                            }
                        }
                    }
                }
            } else {
                if (loading) LinearProgressIndicator(Modifier.fillMaxWidth())
                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                    Button(onClick = { loadUsers() }) { Text("Reintentar") }
                }
                val displayed = if (showFavorites) favorites else users
                if (!loading && displayed.isEmpty() && error == null) {
                    Text(if (showFavorites) "Todavía no has guardado favoritos." else "No hay usuarios para mostrar.")
                }
                LazyColumn {
                    items(displayed, key = { it.id }) { user ->
                        Card(Modifier.fillMaxWidth().padding(vertical = 5.dp)) {
                            Column(Modifier.fillMaxWidth().padding(12.dp)) {
                                Text(user.name, style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.clickable {
                                        scope.launch {
                                            loading = true
                                            error = null
                                            try {
                                                selected = repository.user(user.id)
                                                posts = repository.posts(user.id)
                                            } catch (_: Exception) {
                                                error = "No se pudo cargar el detalle. Comprueba tu conexión."
                                            } finally { loading = false }
                                        }
                                    })
                                Text(user.email)
                                Text(user.company, style = MaterialTheme.typography.bodySmall)
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    TextButton(onClick = {
                                        scope.launch {
                                            loading = true
                                            try {
                                                selected = repository.user(user.id)
                                                posts = repository.posts(user.id)
                                            } catch (_: Exception) {
                                                error = "No se pudo cargar el detalle. Comprueba tu conexión."
                                            } finally { loading = false }
                                        }
                                    }) { Text("Ver detalle") }
                                    TextButton(onClick = { scope.launch { repository.toggleFavorite(user) } }) {
                                        Text(if (favorites.any { it.id == user.id }) "★ Favorito" else "☆ Guardar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
