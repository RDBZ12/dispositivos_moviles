package com.directorio.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class UserDto(
    val id: Int, val name: String, val username: String,
    val email: String, val phone: String, val website: String,
    val company: CompanyDto, val address: AddressDto
)
data class CompanyDto(val name: String, val catchPhrase: String, val bs: String)
data class AddressDto(val street: String, val suite: String, val city: String, val zipcode: String)
data class PostDto(val userId: Int, val id: Int, val title: String, val body: String)

interface DirectoryApi {
    @GET("users") suspend fun getUsers(): List<UserDto>
    @GET("users/{id}") suspend fun getUser(@Path("id") id: Int): UserDto
    @GET("posts") suspend fun getPostsByUser(@Query("userId") userId: Int): List<PostDto>
}
