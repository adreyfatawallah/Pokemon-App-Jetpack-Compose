package com.example.pokemonapp.feature.pokemon.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.feature.pokemon.domain.entity.PokemonEntity
import com.example.pokemonapp.feature.pokemon.domain.repository.PokemonRepository
import com.example.pokemonapp.util.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<RequestState<List<PokemonEntity>>> = MutableStateFlow(
        RequestState.Loading
    )
    val state = _uiState.asStateFlow()

    init {
        getPokemon()
    }

    private fun getPokemon(url: String? = null) {
        viewModelScope.launch {
            val result = repository.getPokemon(url)
            
            _uiState.update {
                if (result.isNotEmpty()) RequestState.Success(result)
                else RequestState.Error("Empty")
            }
        }
    }
}