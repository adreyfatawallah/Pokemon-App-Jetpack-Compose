package com.example.pokemonapp.data.local.user

import com.example.UserTable

interface UserDataSource {
    suspend fun createUser(username: String, password: String) : Long

    suspend fun selectUser(username: String, password: String) : UserTable?
}