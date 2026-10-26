package com.directorio.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "favorite_users")
data class FavoriteUserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val company: String,
    val phone: String,
    val website: String,
    val city: String
)

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_users ORDER BY name")
    fun observeFavorites(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_users WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: FavoriteUserEntity)

    @Query("DELETE FROM favorite_users WHERE id = :id")
    suspend fun remove(id: Int)
}

@Database(entities = [FavoriteUserEntity::class], version = 1, exportSchema = false)
abstract class DirectoryDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
