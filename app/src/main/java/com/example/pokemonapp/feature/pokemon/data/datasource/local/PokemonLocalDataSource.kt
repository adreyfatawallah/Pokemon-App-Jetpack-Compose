package com.example.pokemonapp.feature.pokemon.data.datasource.local

import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity

interface PokemonLocalDataSource {

    suspend fun insertAllPokemon(pokemonList: List<PokemonEntity>)
    suspend fun getAllPokemon() : List<PokemonEntity>
}