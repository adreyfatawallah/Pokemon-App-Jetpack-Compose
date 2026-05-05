package com.example.pokemonapp.feature.pokemon

import com.example.pokemonapp.feature.pokemon.data.datasource.local.PokemonLocalDataSource
import com.example.pokemonapp.feature.pokemon.data.datasource.local.PokemonLocalDataSourceImpl
import com.example.pokemonapp.feature.pokemon.data.datasource.remote.PokemonRemoteDataSource
import com.example.pokemonapp.feature.pokemon.data.datasource.remote.PokemonRemoteDataSourceImpl
import com.example.pokemonapp.feature.pokemon.domain.PokemonRepository
import com.example.pokemonapp.feature.pokemon.domain.PokemonRepositoryImpl
import com.example.pokemonapp.feature.pokemon.presentation.screen.list.ListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pokemonModule = module {
    singleOf(::PokemonRemoteDataSourceImpl).bind<PokemonRemoteDataSource>()
    singleOf(::PokemonLocalDataSourceImpl).bind<PokemonLocalDataSource>()
    singleOf(::PokemonRepositoryImpl).bind<PokemonRepository>()

    viewModelOf(::ListViewModel)
}