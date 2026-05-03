package com.example.pokemonapp.feature.auth.presentation.screen.register

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

sealed class RegisterEvent {
    data class OnRegister(val isSuccess: Boolean, val message: String? = null) : RegisterEvent()
}

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val retypePassword: String = "",

    val isUsernameValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordMatch: Boolean = false,
)

class RegisterViewModel(
    private val authRespository: AuthRespository,
) : ViewModel() {

    private val _eventChannel = Channel<RegisterEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _uiState: MutableState<RegisterUiState> = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            isUsernameValid = username.length > 4
        )
    }

    fun updatePassword(password: String) {
        val retypePassword = _uiState.value.retypePassword

        _uiState.value = _uiState.value.copy(
            password = password,
            isPasswordValid = password.length > 4,
            isPasswordMatch = password.isNotEmpty() && password == retypePassword
        )
    }

    fun updateRetypePassword(retypePassword: String) {
        val password = _uiState.value.password

        _uiState.value = _uiState.value.copy(
            retypePassword = retypePassword,
            isPasswordMatch = password.isNotEmpty() && password == retypePassword
        )
    }

    fun register() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    authRespository.register(
                        UserEntity(
                            username = _uiState.value.username,
                            password = _uiState.value.password
                        )
                    )
                }

                _eventChannel.send(RegisterEvent.OnRegister(result > 0))
            } catch (e: Exception) {
                _eventChannel.send(RegisterEvent.OnRegister(false, e.message))
            }
        }
    }
}