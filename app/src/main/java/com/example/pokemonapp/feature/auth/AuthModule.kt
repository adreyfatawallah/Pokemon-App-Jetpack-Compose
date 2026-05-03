package com.example.pokemonapp.feature.auth

import com.example.pokemonapp.feature.auth.data.datasource.local.LocalDataSource
import com.example.pokemonapp.feature.auth.data.datasource.local.LocalDataSourceImpl
import com.example.pokemonapp.feature.auth.domain.AuthRepositoryImpl
import com.example.pokemonapp.feature.auth.domain.AuthRespository
import com.example.pokemonapp.feature.auth.presentation.screen.login.LoginViewModel
import com.example.pokemonapp.feature.auth.presentation.screen.register.RegisterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::LocalDataSourceImpl).bind<LocalDataSource>()
    singleOf(::AuthRepositoryImpl).bind<AuthRespository>()

    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}