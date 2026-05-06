package com.example.pokemonapp.feature.pokemon.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity
import com.example.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun ItemPokemon(
    modifier: Modifier = Modifier,
    pokemon: PokemonEntity
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                imageVector = Icons.Default.Image,
                contentDescription = "image"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = pokemon.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPokemonPreview() {
    PokemonAppTheme {
        ItemPokemon(
            pokemon = PokemonEntity(
                name = "Pikachu",
                url = ""
            )
        )
    }
}