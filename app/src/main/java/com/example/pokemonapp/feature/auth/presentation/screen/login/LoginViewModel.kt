package com.example.pokemonapp.feature.auth.presentation.screen.login

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

sealed interface OnLoginState {
    data object Idle : OnLoginState
    data object Loading : OnLoginState
    class Success(val message: String) : OnLoginState
    class Failure(val message: String) : OnLoginState
}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
)

class LoginViewModel(
    private val app: Application,
    private val authRespository: AuthRespository,
) : ViewModel() {

    private val _onLoginState: MutableStateFlow<OnLoginState> = MutableStateFlow(OnLoginState.Idle)
    val onLoginState = _onLoginState.asStateFlow()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(username = username)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun login() {
        if (_onLoginState.value is OnLoginState.Loading)
            return

        viewModelScope.launch {
            _onLoginState.value = OnLoginState.Loading

            val result = withContext(Dispatchers.IO) {
                authRespository.login(
                    UserEntity(
                        username = _uiState.value.username,
                        password = _uiState.value.password
                    )
                )
            }

            val isSuccess = result != null
            val message = if (isSuccess) app.getString(R.string.msg_login_success, _uiState.value.username)
            else app.getString(R.string.msg_login_failure)

            _onLoginState.value = if (isSuccess) OnLoginState.Success(message = message)
            else OnLoginState.Failure(message = message)
        }
    }

    fun resetOnLoginState() {
        _onLoginState.value = OnLoginState.Idle
    }
}