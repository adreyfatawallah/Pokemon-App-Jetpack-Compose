package com.example.pokemonapp.feature.pokemon.presentation.screen.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokemonapp.R
import com.example.pokemonapp.component.EmptyCard
import com.example.pokemonapp.component.LoadingCard
import com.example.pokemonapp.feature.pokemon.presentation.component.HeaderNavDrawer
import com.example.pokemonapp.feature.pokemon.presentation.component.ItemPokemon
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import com.example.pokemonapp.ui.theme.paddingDefault
import com.example.pokemonapp.ui.theme.paddingExSmall
import com.example.pokemonapp.ui.theme.paddingSmall
import com.example.pokemonapp.util.DisplayResult
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen() {
    val viewModel = koinViewModel<ListViewModel>()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuNavDrawer: List<Map<String, List<String>>> = listOf(
        mapOf(
            "Main Menu" to listOf("Menu 1", "Menu 2", "Menu 3"),
            "Setting" to listOf("Offline", "Logout")
        ),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                windowInsets = WindowInsets(top = 0, bottom = 0)
            ) {
                val statusBarHeight =
                    WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()

                HeaderNavDrawer(statusBarHeight = statusBarHeight)

                Column(
                    verticalArrangement = Arrangement.spacedBy(paddingSmall),
                    modifier = Modifier.padding(paddingDefault)
                ) {
                    val allMenus = menuNavDrawer.flatMap { it.entries }
                    allMenus.forEachIndexed { index, entry ->
                        val (titleMenu, itemMenu) = entry
                        Text(text = titleMenu, style = MaterialTheme.typography.titleLarge)
                        itemMenu.forEach { item ->
                            Text(
                                modifier = Modifier.padding(horizontal = paddingDefault, vertical = paddingSmall),
                                text = item,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        if (index < allMenus.size - 1) {
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(paddingSmall))
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(R.string.app_name))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                        }
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
                onError = { message ->
                    EmptyCard(text = message)
                }
            )
        }
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    PokemonAppTheme {
        ListScreen()
    }
}