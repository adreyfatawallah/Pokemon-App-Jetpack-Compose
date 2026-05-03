package com.example.pokemonapp.feature.auth.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object AuthGraph

@Serializable
sealed class AuthRoute {
    @Serializable
    object Login : AuthRoute()

    @Serializable
    object Register : AuthRoute()
}