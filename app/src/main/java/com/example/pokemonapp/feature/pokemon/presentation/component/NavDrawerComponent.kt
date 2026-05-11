package com.example.pokemonapp.feature.pokemon.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.R
import com.example.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun HeaderNavDrawer(
    modifier: Modifier = Modifier,
    statusBarHeight: Dp,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(top = statusBarHeight),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
        )
    }
}

@Preview
@Composable
private fun HeaderNavDrawerPreview() {
    PokemonAppTheme {
        HeaderNavDrawer(statusBarHeight = 0.dp)
    }
}