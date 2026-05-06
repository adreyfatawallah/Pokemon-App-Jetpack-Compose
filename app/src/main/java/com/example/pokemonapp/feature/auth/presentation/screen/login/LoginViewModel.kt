package com.example.pokemonapp.feature.auth.presentation.screen.login

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

sealed class OnLoginEvent(val message: String) {
    class Success(message: String) : OnLoginEvent(message)
    class Failure(message: String) : OnLoginEvent(message)
}

data class LoginUiState(
    val username: String = "",
    val password: String = "",

    val isLoading: Boolean? = null,
)

class LoginViewModel(
    private val app: Application,
    private val authRespository: AuthRespository,
) : ViewModel() {

    private val _eventChannel = Channel<OnLoginEvent>()
    val event = _eventChannel.receiveAsFlow()

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
        if (_uiState.value.isLoading == true)
            return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            val result = withContext(Dispatchers.IO) {
                authRespository.login(
                    UserEntity(
                        username = _uiState.value.username,
                        password = _uiState.value.password
                    )
                )
            }

            _eventChannel.send(
                if (result != null) OnLoginEvent.Success(app.getString(R.string.msg_login_success))
                else OnLoginEvent.Failure(app.getString(R.string.msg_login_failure))
            )

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