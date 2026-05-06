package com.example.pokemonapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemonapp.R
import com.example.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun EmptyCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    text: String = stringResource(R.string.lbl_empty)
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun EmptyCardPreview() {
    PokemonAppTheme {
        EmptyCard()
    }
}