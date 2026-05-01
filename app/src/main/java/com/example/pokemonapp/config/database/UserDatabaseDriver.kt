package com.example.pokemonapp.config.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.UserDatabase

class UserDatabaseDriver(
    private val context: Context
) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            UserDatabase.Schema,
            context,
            "user.db"
        )
    }
}