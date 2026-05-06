package com.example.pokemonapp.feature.auth.presentation.screen.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.R
import com.example.pokemonapp.feature.auth.domain.AuthRespository
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class OnRegisterEvent {
    object Success: OnRegisterEvent()
    data class Failure(val message: String): OnRegisterEvent()
}

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val retypePassword: String = "",

    val isUsernameValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordMatch: Boolean = false,

    val isLoading: Boolean? = null
)

class RegisterViewModel(
    private val app: Application,
    private val authRespository: AuthRespository,
) : ViewModel() {

    private val _eventChannel = Channel<OnRegisterEvent>()
    val events = _eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(
                username = username,
                isUsernameValid = username.length > 4
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { current ->
            current.copy(
                password = password,
                isPasswordValid = password.length > 4,
                isPasswordMatch = password.isNotEmpty() && password == current.retypePassword
            )
        }
    }

    fun updateRetypePassword(retypePassword: String) {
        _uiState.update { current ->
            current.copy(
                retypePassword = retypePassword,
                isPasswordMatch = current.password.isNotEmpty() && current.password == retypePassword
            )
        }
    }

    fun register() {
        if (_uiState.value.isLoading == true)
            return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            val msgFailure = app.getString(R.string.msg_register_failure)

            try {
                val result = withContext(Dispatchers.IO) {
                    authRespository.register(
                        UserEntity(
                            username = _uiState.value.username,
                            password = _uiState.value.password
                        )
                    )
                }

                _eventChannel.send(
                    if (result > 0) OnRegisterEvent.Success
                    else OnRegisterEvent.Failure(msgFailure)
                )
            } catch (e: Exception) {
                _eventChannel.send(
                    OnRegisterEvent.Failure(message = e.message ?: msgFailure)
                )
            }

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun resetLoading() {
        _uiState.update {
            it.copy(isLoading = null)
        }
    }
}