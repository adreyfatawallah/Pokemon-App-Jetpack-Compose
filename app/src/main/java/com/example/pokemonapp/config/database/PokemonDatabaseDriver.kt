package com.example.pokemonapp.config.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.PokemonDatabase

class PokemonDatabaseDriver(
    private val context: Context
) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            PokemonDatabase.Schema,
            context,
            "pokemon.db"
        )
    }
}