package com.example.pokemonapp.main.presentation.screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.R
import com.example.pokemonapp.main.domain.repository.MainRepository
import com.example.pokemonapp.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    app: Application,
    mainRepository: MainRepository
) : ViewModel() {

    val hasLogin: StateFlow<RequestState<Boolean>> = mainRepository.hasLogin()
        .catch { error ->
            RequestState.Error(error.message ?: app.getString(R.string.msg_data_store_error))
        }
        .map { hasLogin ->
            RequestState.Success(hasLogin)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            RequestState.Loading
        )
}