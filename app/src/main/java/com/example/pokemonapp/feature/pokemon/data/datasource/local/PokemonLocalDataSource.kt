package com.example.pokemonapp.feature.pokemon.data.datasource.local

import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity

interface PokemonLocalDataSource {

    fun insertAllPokemon(pokemonList: List<PokemonEntity>)
    fun getAllPokemon() : List<PokemonEntity>
}