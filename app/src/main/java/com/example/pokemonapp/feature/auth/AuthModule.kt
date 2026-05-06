package com.example.pokemonapp.feature.auth

import com.example.pokemonapp.feature.auth.data.datasource.local.AuthLocalDataSource
import com.example.pokemonapp.feature.auth.data.datasource.local.AuthLocalDataSourceImpl
import com.example.pokemonapp.feature.auth.domain.repository.AuthRepositoryImpl
import com.example.pokemonapp.feature.auth.domain.repository.AuthRespository
import com.example.pokemonapp.feature.auth.presentation.screen.login.LoginViewModel
import com.example.pokemonapp.feature.auth.presentation.screen.register.RegisterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthLocalDataSourceImpl).bind<AuthLocalDataSource>()
    singleOf(::AuthRepositoryImpl).bind<AuthRespository>()

    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}