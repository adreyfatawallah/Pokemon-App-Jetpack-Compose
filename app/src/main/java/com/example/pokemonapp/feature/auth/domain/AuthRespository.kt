package com.example.pokemonapp.feature.auth.domain

import com.example.pokemonapp.feature.auth.data.model.UserModel
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity

interface AuthRespository {

    suspend fun login(userEntity: UserEntity): UserModel?

    suspend fun register(userEntity: UserEntity): Long

    suspend fun updatePreferenceLogin(username: String)
}