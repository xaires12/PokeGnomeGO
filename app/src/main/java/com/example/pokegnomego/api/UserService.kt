package com.example.pokegnomego.com.example.pokegnomego.api

import retrofit2.Call
import retrofit2.http.GET

data class User(val login: String, val visit_count: Int)

interface UserService {
    @GET("users")
    fun getUsers(): Call<List<User>>
    // nienawidze kotlina
}