package com.directorio.app.data.repository

import com.directorio.app.data.local.FavoriteDao
import com.directorio.app.data.local.FavoriteUserEntity
import com.directorio.app.data.remote.DirectoryApi
import com.directorio.app.data.remote.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class User(
    val id: Int, val name: String, val username: String, val email: String,
    val company: String, val phone: String, val website: String, val city: String
)
data class Post(val id: Int, val title: String, val body: String)

class DirectoryRepository(private val api: DirectoryApi, private val dao: FavoriteDao) {
    suspend fun users(): List<User> = api.getUsers().map { it.toUser() }
    suspend fun user(id: Int): User = api.getUser(id).toUser()
    suspend fun posts(id: Int): List<Post> = api.getPostsByUser(id).map { Post(it.id, it.title, it.body) }
    fun favorites(): Flow<List<User>> = dao.observeFavorites().map { rows ->
        rows.map { User(it.id, it.name, "", it.email, it.company, it.phone, it.website, it.city) }
    }
    suspend fun isFavorite(id: Int) = dao.isFavorite(id)
    suspend fun toggleFavorite(user: User) {
        if (dao.isFavorite(user.id)) dao.remove(user.id)
        else dao.add(FavoriteUserEntity(user.id, user.name, user.email, user.company, user.phone, user.website, user.city))
    }
    private fun UserDto.toUser() = User(id, name, username, email, company.name, phone, website, address.city)
}
