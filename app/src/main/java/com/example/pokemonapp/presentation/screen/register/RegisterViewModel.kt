package com.example.pokemonapp.presentation.screen.register

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

sealed class RegisterEvent {
    data class OnRegister(val isSuccess: Boolean, val message: String? = null): RegisterEvent()
}

class RegisterViewModel(
    private val userDataSource: UserDataSource
): ViewModel() {

    private val _eventChannel = Channel<RegisterEvent>()
    val events = _eventChannel.receiveAsFlow()

    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var retypePassword by mutableStateOf("")
        private set

    val isUsernameValid get() = username.length > 4
    val isPasswordValid get() = password.length > 4
    val isPasswordMatch get() = password.isNotEmpty() && password == retypePassword

    fun updateUsername(string: String) {
        username = string
    }

    fun updatePassword(string: String) {
        password = string
    }

    fun updateRetypePassword(string: String) {
        retypePassword = string
    }

    fun register() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    userDataSource.createUser(username = username, password = password)
                }

                _eventChannel.send(RegisterEvent.OnRegister(result > 0))
            } catch (e: Exception) {
                _eventChannel.send(RegisterEvent.OnRegister(false, e.message))
            }
        }
    }
}