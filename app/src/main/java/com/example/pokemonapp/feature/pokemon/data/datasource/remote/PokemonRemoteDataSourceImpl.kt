package com.example.pokemonapp.feature.pokemon.data.datasource.remote

import com.example.pokemonapp.config.network.constructUrl
import com.example.pokemonapp.config.network.result.NetworkError
import com.example.pokemonapp.config.network.result.Result
import com.example.pokemonapp.config.network.safeCall
import com.example.pokemonapp.feature.pokemon.data.model.PokemonListModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class PokemonRemoteDataSourceImpl(
    private val httpClient: HttpClient
): PokemonRemoteDataSource {

    override suspend fun getPokemon(
        url: String?
    ): Result<PokemonListModel, NetworkError> {
        return safeCall<PokemonListModel> {
            httpClient.get(url ?: constructUrl("/pokemon"))
        }
    }
}