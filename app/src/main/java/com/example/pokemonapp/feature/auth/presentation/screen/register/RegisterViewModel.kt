package com.example.pokemonapp.feature.auth.presentation.screen.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.R
import com.example.pokemonapp.feature.auth.domain.repository.AuthRespository
import com.example.pokemonapp.feature.auth.domain.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface OnRegisterState {
    data object Idle : OnRegisterState
    data object Loading : OnRegisterState
    data class Success(val message: String) : OnRegisterState
    data class Failure(val message: String) : OnRegisterState
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
    private val app: Application,
    private val authRespository: AuthRespository,
) : ViewModel() {

    private val _onRegisterState: MutableStateFlow<OnRegisterState> =
        MutableStateFlow(OnRegisterState.Idle)
    val onRegisterState = _onRegisterState.asStateFlow()

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
        if (_onRegisterState.value is OnRegisterState.Loading)
            return

        viewModelScope.launch {
            _onRegisterState.value = OnRegisterState.Loading

            val msgSuccess = app.getString(R.string.msg_register_success)
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

                val isSuccess = result > 0
                _onRegisterState.value =
                    if (isSuccess) OnRegisterState.Success(message = msgSuccess)
                    else OnRegisterState.Failure(message = msgFailure)
            } catch (e: Exception) {
                _onRegisterState.value = OnRegisterState.Failure(message = e.message ?: msgFailure)
            }
        }
    }

    fun resetOnRegisterState() {
        _onRegisterState.value = OnRegisterState.Idle
    }
}