package com.example.pokemonapp.feature.auth.data.datasource.local

import com.example.pokemonapp.feature.auth.data.model.UserModel
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity

interface AuthLocalDataSource {
    suspend fun createUser(userEntity: UserEntity) : Long

    fun selectUser(userEntity: UserEntity) : UserModel?
}