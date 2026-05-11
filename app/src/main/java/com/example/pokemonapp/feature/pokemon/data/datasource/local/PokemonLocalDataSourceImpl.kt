package com.example.pokemonapp.feature.pokemon.data.datasource.local

import com.example.PokemonDatabase
import com.example.pokemonapp.config.database.PokemonDatabaseDriver
import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity
import com.example.pokemonapp.feature.pokemon.domain.entity.toPokemonEntity

class PokemonLocalDataSourceImpl(
    pokemonDatabaseDriver: PokemonDatabaseDriver
): PokemonLocalDataSource {
    
    private val database = PokemonDatabase(
        pokemonDatabaseDriver.createDriver()
    )

    private val query = database.pokemonDatabaseQueries

    override fun insertAllPokemon(pokemonList: List<PokemonEntity>) {
        query.transaction {
            pokemonList.forEach { pokemon ->
                query.insertOrReplacePokemon(
                    name = pokemon.name,
                    url = pokemon.url
                )
            }
        }
    }

    override fun getAllPokemon(): List<PokemonEntity> {
        return query.getAllPokemon().executeAsList()
            .map { it.toPokemonEntity() }
    }
}