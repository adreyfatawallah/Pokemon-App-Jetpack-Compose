package com.example.pokemonapp

import com.example.pokemonapp.config.database.PokemonDatabaseDriver
import com.example.pokemonapp.config.database.UserDatabaseDriver
import com.example.pokemonapp.config.network.MyHttpClient
import com.example.pokemonapp.config.preference.MyPreference
import com.example.pokemonapp.main.domain.MainRepository
import com.example.pokemonapp.main.domain.MainRepositoryImpl
import com.example.pokemonapp.main.presentation.screen.MainViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val myModule = module {
    single { MyHttpClient.create(CIO.create()) }

    singleOf(::UserDatabaseDriver)
    singleOf(::PokemonDatabaseDriver)
    singleOf(::MyPreference)

    singleOf(::MainRepositoryImpl).bind<MainRepository>()

    viewModelOf(::MainViewModel)
}