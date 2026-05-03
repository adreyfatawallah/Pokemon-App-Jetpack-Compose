package com.example.pokemonapp.feature.pokemon.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object PokemonGraph

@Serializable
sealed class PokemonRoute {
    @Serializable
    object List : PokemonRoute()
}