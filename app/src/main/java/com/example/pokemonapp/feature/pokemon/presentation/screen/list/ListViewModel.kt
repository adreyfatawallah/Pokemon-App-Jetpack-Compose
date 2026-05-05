package com.example.pokemonapp.feature.pokemon.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.feature.pokemon.domain.PokemonRepository
import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: PokemonRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(emptyList<PokemonEntity>())
    val state = _uiState
        .onStart { getPokemon() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun getPokemon() {
        viewModelScope.launch {
            val result = repository.getPokemon()
            _uiState.update { result }
        }
    }
}