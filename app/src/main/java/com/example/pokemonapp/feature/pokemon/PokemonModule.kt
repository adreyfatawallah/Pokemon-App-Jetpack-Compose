package com.example.pokemonapp.feature.pokemon

import com.example.pokemonapp.feature.pokemon.presentation.screen.list.ListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val pokemonModule = module {
    viewModelOf(::ListViewModel)
}