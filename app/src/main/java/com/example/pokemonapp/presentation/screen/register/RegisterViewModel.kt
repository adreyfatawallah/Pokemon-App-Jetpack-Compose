package com.example.pokemonapp.presentation.screen.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {

    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var retypePassword by mutableStateOf("")
        private set

    var isUsernameValid = false
        private set
    var isPasswordValid = false
        private set
    var isPasswordMatch = false
        private set

    fun updateUsername(string: String) {
        username = string
        isUsernameValid = username.length > 4
    }

    fun updatePassword(string: String) {
        password = string
        isPasswordValid = password.length > 4
    }

    fun updateRetypePassword(string: String) {
        retypePassword = string
        isPasswordMatch = password == retypePassword
    }
}