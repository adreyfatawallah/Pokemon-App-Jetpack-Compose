package com.example.pokemonapp.feature.pokemon.data.datasource.local

import com.example.PokemonDatabase
import com.example.pokemonapp.config.database.PokemonDatabaseDriver
import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity

class PokemonLocalDataSourceImpl(
    pokemonDatabaseDriver: PokemonDatabaseDriver
): PokemonLocalDataSource {
    
    private val database = PokemonDatabase(
        pokemonDatabaseDriver.createDriver()
    )

    private val query = database.pokemonDatabaseQueries

    override suspend fun insertAllPokemon(pokemonList: List<PokemonEntity>) {
        query.transaction {
            pokemonList.forEach { pokemon ->
                query.insertOrReplacePokemon(
                    name = pokemon.name,
                    url = pokemon.url
                )
            }
        }
    }

    override suspend fun getAllPokemon(): List<PokemonEntity> {
        return query.getAllPokemon().executeAsList()
            .map { pokemonTable ->
                PokemonEntity(
                    name = pokemonTable.name,
                    url = pokemonTable.url
                )
            }
    }
}