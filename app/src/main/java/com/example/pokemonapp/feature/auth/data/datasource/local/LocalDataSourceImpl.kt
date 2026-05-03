package com.example.pokemonapp.feature.auth.data.datasource.local

import com.example.UserDatabase
import com.example.pokemonapp.config.database.UserDatabaseDriver
import com.example.pokemonapp.feature.auth.data.model.UserModel
import com.example.pokemonapp.feature.auth.data.model.toModel
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity

class LocalDataSourceImpl(
    userDatabaseDriver: UserDatabaseDriver
) : LocalDataSource {

    private val database = UserDatabase(
        userDatabaseDriver.createDriver()
    )

    private val query = database.userDatabaseQueries

    override suspend fun createUser(userEntity: UserEntity): Long {
        return query.insertUser(userEntity.username, userEntity.password).await()
    }

    override suspend fun selectUser(userEntity: UserEntity): UserModel? {
        return query.selectUser(userEntity.username, userEntity.password).executeAsOneOrNull()?.toModel()
    }
}