package com.example.pokemonapp.main.presentation.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.pokemonapp.component.LoadingCard
import com.example.pokemonapp.feature.auth.presentation.navigation.AuthGraph
import com.example.pokemonapp.feature.auth.presentation.navigation.authNav
import com.example.pokemonapp.feature.pokemon.presentation.navigation.PokemonGraph
import com.example.pokemonapp.feature.pokemon.presentation.navigation.pokemonNav
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.util.DisplayResult
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonAppTheme {
                val viewModel = koinViewModel<MainViewModel>()
                val hasLogin by viewModel.hasLogin.collectAsStateWithLifecycle()

                hasLogin.DisplayResult(
                    onLoading = { LoadingCard() },
                    onSuccess = { hasLogin -> SetupNavHost(hasLogin) },
                    onError = { message ->
                        Log.e("adrey", "error: $message")

                        SetupNavHost(false)
                    }
                )
            }
        }
    }
}

@Composable
fun SetupNavHost(hasLogin: Boolean) {
    val navController = rememberNavController()

    LaunchedEffect(hasLogin) {
        if (!hasLogin) {
            val currentDestination = navController.currentBackStackEntry?.destination
            if (currentDestination?.hasRoute<AuthGraph>() == true) {
                navController.navigate(AuthGraph) {
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (hasLogin) PokemonGraph else AuthGraph,
    ) {
        authNav(navController)
        pokemonNav(navController)
    }
}
