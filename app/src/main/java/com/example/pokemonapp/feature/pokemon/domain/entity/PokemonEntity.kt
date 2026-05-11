package com.example.pokemonapp.feature.pokemon.domain.entity

import com.example.PokemonTable
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

fun PokemonModel.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        url = url
    )
}

fun PokemonTable.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        url = url
    )
}