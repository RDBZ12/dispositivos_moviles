package com.directorio.app

import android.app.Application
import androidx.room.Room
import com.directorio.app.data.local.DirectoryDatabase
import com.directorio.app.data.remote.DirectoryApi
import com.directorio.app.data.repository.DirectoryRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DirectoryApplication : Application() {
    lateinit var repository: DirectoryRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DirectoryApi::class.java)
        val database = Room.databaseBuilder(
            applicationContext, DirectoryDatabase::class.java, "directory.db"
        ).build()
        repository = DirectoryRepository(api, database.favoriteDao())
    }
}
