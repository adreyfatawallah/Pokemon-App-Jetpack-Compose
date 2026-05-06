package com.example.pokemonapp.feature.auth.domain.repository

import com.example.pokemonapp.config.preference.MyPreference
import com.example.pokemonapp.feature.auth.data.model.UserModel
import com.example.pokemonapp.feature.auth.data.datasource.local.AuthLocalDataSource
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity

class AuthRepositoryImpl(
    private val localDataSource: AuthLocalDataSource,
    private val preference: MyPreference
): AuthRespository {

    override suspend fun login(userEntity: UserEntity): UserModel? {
        val result = localDataSource.selectUser(userEntity)

        if (result != null) {
            preference.updateStatusLogin(true)
            preference.saveUsername(userEntity.username)
        }

        return result
    }

    override suspend fun register(userEntity: UserEntity): Long {
        return localDataSource.createUser(userEntity)
    }
}