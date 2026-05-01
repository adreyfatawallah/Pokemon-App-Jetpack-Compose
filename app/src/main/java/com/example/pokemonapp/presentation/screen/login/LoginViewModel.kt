package com.example.pokemonapp.presentation.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.local.user.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class LoginEvent {
    data class OnLogin(val isSuccess: Boolean): LoginEvent()
}

class LoginViewModel(
    private val userDataSource: UserDataSource
): ViewModel() {

    private val _eventChannel = Channel<LoginEvent>()
    val event = _eventChannel.receiveAsFlow()

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateUsername(string: String) {
        username = string
    }

    fun updatePassword(string: String) {
        password = string
    }

    fun login() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                userDataSource.selectUser(username = username, password = password)
            }

            _eventChannel.send(LoginEvent.OnLogin(result != null))
        }
    }
}