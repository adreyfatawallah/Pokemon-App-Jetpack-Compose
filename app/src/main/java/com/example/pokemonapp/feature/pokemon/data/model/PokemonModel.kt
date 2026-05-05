package com.example.pokemonapp.feature.pokemon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListModel(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<PokemonModel>
)

@Serializable
data class PokemonModel(
    val name: String,
    val url: String
)