package com.example.pokemonapp.feature.pokemon.domain

import android.util.Log
import com.example.pokemonapp.config.network.result.onError
import com.example.pokemonapp.config.network.result.onSuccess
import com.example.pokemonapp.feature.pokemon.data.datasource.local.PokemonLocalDataSource
import com.example.pokemonapp.feature.pokemon.data.datasource.remote.PokemonRemoteDataSource
import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity

class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
): PokemonRepository {

    override suspend fun getPokemon(url: String?): List<PokemonEntity> {
        var listPokemon = emptyList<PokemonEntity>()

        remoteDataSource.getPokemon(url = url)
            .onSuccess { result ->
                Log.e("adrey", "success: $result")

                listPokemon = result.results.map { pokemonModel ->
                    PokemonEntity(
                        name = pokemonModel.name,
                        url = pokemonModel.url
                    )
                }

                localDataSource.insertAllPokemon(listPokemon)
            }
            .onError { error ->
                Log.e("adrey", "error: $error")

                listPokemon = localDataSource.getAllPokemon()
            }

        return listPokemon
    }
}