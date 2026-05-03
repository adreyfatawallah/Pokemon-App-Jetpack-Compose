package com.example.pokemonapp.feature.auth.presentation.screen.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity
import com.example.pokemonapp.feature.auth.domain.AuthRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class LoginEvent {
    data class OnLogin(val isSuccess: Boolean) : LoginEvent()
}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
)

class LoginViewModel(
    private val authRespository: AuthRespository,
) : ViewModel() {

    private val _eventChannel = Channel<LoginEvent>()
    val event = _eventChannel.receiveAsFlow()

    private val _uiState: MutableState<LoginUiState> = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                authRespository.login(
                    UserEntity(
                        username = _uiState.value.username,
                        password = _uiState.value.password
                    )
                )
            }

            if (result != null) {
                authRespository.updatePreferenceLogin(_uiState.value.username)
            }

            _eventChannel.send(LoginEvent.OnLogin(result != null))
        }
    }
}