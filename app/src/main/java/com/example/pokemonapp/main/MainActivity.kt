package com.example.pokemonapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.pokemonapp.feature.auth.presentation.navigation.AuthGraph
import com.example.pokemonapp.feature.auth.presentation.navigation.authNav
import com.example.pokemonapp.feature.pokemon.presentation.navigation.PokemonGraph
import com.example.pokemonapp.feature.pokemon.presentation.navigation.pokemonNav
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonAppTheme {
                val navController = rememberNavController()
                val viewModel = koinViewModel<MainViewModel>()

                val hasLogin by viewModel.hasLogin.collectAsStateWithLifecycle()
                if (hasLogin == null) {
                    CircularProgressIndicator()
                    return@PokemonAppTheme
                }

                // not verified
                LaunchedEffect(hasLogin) {
                    if (hasLogin == false) {
                        navController.navigate(AuthGraph) {
                            popUpTo<PokemonGraph> {
                                saveState = true
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = if (hasLogin == true) PokemonGraph else AuthGraph,
                ) {
                    authNav(navController)
                    pokemonNav(navController)
                }
            }
        }
    }
}