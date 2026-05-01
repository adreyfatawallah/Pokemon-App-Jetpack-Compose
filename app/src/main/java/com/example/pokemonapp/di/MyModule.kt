package com.example.pokemonapp.di

import com.example.pokemonapp.config.database.UserDatabaseDriver
import com.example.pokemonapp.data.local.user.UserDataSource
import com.example.pokemonapp.data.local.user.UserDataSourceImpl
import com.example.pokemonapp.presentation.screen.login.LoginViewModel
import com.example.pokemonapp.presentation.screen.register.RegisterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val singleModule = module {
    singleOf(::UserDatabaseDriver)
    singleOf(::UserDataSourceImpl).bind<UserDataSource>()
}

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}