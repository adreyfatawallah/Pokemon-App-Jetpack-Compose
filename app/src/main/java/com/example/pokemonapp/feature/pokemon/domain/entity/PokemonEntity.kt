package com.example.pokemonapp.feature.pokemon.domain.entity

import com.example.pokemonapp.feature.pokemon.data.model.PokemonListModel
import com.example.pokemonapp.feature.pokemon.data.model.PokemonModel

data class PokemonListEntity(
    val next: String,
    val results: List<PokemonEntity>
)

data class PokemonEntity(
    val name: String,
    val url: String
)

fun PokemonListModel.toPokemonListEntity(): PokemonListEntity {
    return PokemonListEntity(
        next = next,
        results = results.map { result -> result.toPokemonEntity() }
    )
}

fun PokemonModel.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        url = url
    )
}