package com.example.pokemonapp.feature.auth.data.model

import com.example.UserTable

data class UserModel(
    val id: Long,
    val username: String,
    val password: String
)

fun UserTable.toModel() = UserModel(
    id = this.id,
    username = this.username,
    password = this.password
)
