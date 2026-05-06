package com.example.pokemonapp.feature.pokemon.presentation.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokemonapp.R
import com.example.pokemonapp.component.LoadingCard
import com.example.pokemonapp.feature.pokemon.presentation.component.ItemPokemon
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.util.DisplayResult
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen() {
    val viewModel = koinViewModel<ListViewModel>()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.app_name))
                }
            )
        }
    ) { innerPadding ->
        uiState.DisplayResult(
            modifier = Modifier.padding(innerPadding),
            onLoading = { LoadingCard() },
            onSuccess = { listPokemon ->
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(listPokemon) { pokemon ->
                        ItemPokemon(pokemon = pokemon)
                    }
                }
            },
            onError = {}
        )
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    PokemonAppTheme {
        ListScreen()
    }
}