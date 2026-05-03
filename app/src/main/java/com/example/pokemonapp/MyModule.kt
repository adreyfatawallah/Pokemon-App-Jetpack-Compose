package com.example.pokemonapp

import com.example.pokemonapp.config.database.UserDatabaseDriver
import com.example.pokemonapp.config.preference.UserPreference
import com.example.pokemonapp.main.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val myModule = module {
    singleOf(::UserDatabaseDriver)
    singleOf(::UserPreference)

    viewModelOf(::MainViewModel)
}