package com.example.pokemonapp.feature.pokemon.domain.repository

import com.example.pokemonapp.config.network.result.onError
import com.example.pokemonapp.config.network.result.onSuccess
import com.example.pokemonapp.feature.pokemon.data.datasource.local.PokemonLocalDataSource
import com.example.pokemonapp.feature.pokemon.data.datasource.remote.PokemonRemoteDataSource
import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity
import com.example.pokemonapp.feature.pokemon.domain.entity.toPokemonEntity

class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
): PokemonRepository {

    override suspend fun getPokemon(url: String?): List<PokemonEntity> {
        var listPokemon = emptyList<PokemonEntity>()

        remoteDataSource.getPokemon(url = url)
            .onSuccess { result ->
                listPokemon = result.results.map { it.toPokemonEntity() }

                localDataSource.insertAllPokemon(listPokemon)
            }
            .onError {
                listPokemon = localDataSource.getAllPokemon()
            }

        return listPokemon
    }
}