package com.example.pokemonapp.feature.pokemon.domain.repository

import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity

interface PokemonRepository {
    suspend fun getPokemon(url: String?) : List<PokemonEntity>
}