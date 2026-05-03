package com.example.pokemonapp.util

import androidx.navigation.NavController
import com.example.pokemonapp.feature.auth.presentation.navigation.AuthGraph

fun NavController.navigateToLoginAndReturn() {
    val currentRoute = currentBackStackEntry?.destination?.route

    navigate(AuthGraph) {
        popUpTo(graph.startDestinationId) { saveState = true }
    }
}