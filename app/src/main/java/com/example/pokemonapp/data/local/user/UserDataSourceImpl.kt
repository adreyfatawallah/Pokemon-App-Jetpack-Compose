package com.example.pokemonapp.data.local.user

import com.example.UserDatabase
import com.example.UserTable
import com.example.pokemonapp.config.database.UserDatabaseDriver

class UserDataSourceImpl(
    userDatabaseDriver: UserDatabaseDriver
) : UserDataSource {

    private val database = UserDatabase(
        userDatabaseDriver.createDriver()
    )

    private val query = database.userDatabaseQueries

    override suspend fun createUser(username: String, password: String): Long {
        return query.insertUser(username, password).await()
    }

    override suspend fun selectUser(username: String, password: String): UserTable? {
        return query.selectUser(username, password).executeAsOneOrNull()
    }
}