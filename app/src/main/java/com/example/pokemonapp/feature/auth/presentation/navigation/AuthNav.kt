package com.example.pokemonapp.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pokemonapp.feature.auth.presentation.screen.login.LoginScreen
import com.example.pokemonapp.feature.auth.presentation.screen.register.RegisterScreen
import com.example.pokemonapp.feature.pokemon.presentation.navigation.PokemonGraph

fun NavGraphBuilder.authNav(
    navController: NavController,
) {
    navigation<AuthGraph>(
        startDestination = AuthRoute.Login
    ) {
        composable<AuthRoute.Login> {
            LoginScreen(
                navigateToList = {
                    navController.navigate(PokemonGraph)
                },
                navigateToRegister = {
                    navController.navigate(AuthRoute.Register)
                }
            )
        }
        composable<AuthRoute.Register> {
            RegisterScreen(navigateBack = { navController.navigateUp() })
        }
    }
}