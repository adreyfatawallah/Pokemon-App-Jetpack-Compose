package com.example.pokemonapp.feature.pokemon.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pokemonapp.feature.pokemon.presentation.screen.list.ListScreen

fun NavGraphBuilder.pokemonNav(
    navController: NavController
) {
    navigation<PokemonGraph>(
        startDestination = PokemonRoute.List
    ) {
        composable<PokemonRoute.List> {
            ListScreen()
        }
    }
}