package com.example.pokemonapp.feature.pokemon.data.datasource.remote

import com.example.pokemonapp.config.network.result.NetworkError
import com.example.pokemonapp.config.network.result.Result
import com.example.pokemonapp.feature.pokemon.data.model.PokemonListModel

interface PokemonRemoteDataSource {

    suspend fun getPokemon(url: String?) : Result<PokemonListModel, NetworkError>
}