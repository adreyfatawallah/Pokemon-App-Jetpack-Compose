package com.example.pokemonapp.feature.auth.domain

import com.example.pokemonapp.config.preference.UserPreference
import com.example.pokemonapp.feature.auth.data.model.UserModel
import com.example.pokemonapp.feature.auth.data.datasource.local.LocalDataSource
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity

class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val preference: UserPreference
): AuthRespository {

    override suspend fun login(userEntity: UserEntity): UserModel? {
        return localDataSource.selectUser(userEntity)
    }

    override suspend fun register(userEntity: UserEntity): Long {
        return localDataSource.createUser(userEntity)
    }

    override suspend fun updatePreferenceLogin(username: String) {
        preference.updateStatusLogin(true)
        preference.saveUsername(username)
    }
}