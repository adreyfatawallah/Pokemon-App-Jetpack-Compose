package com.example.pokemonapp.presentation.screen.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.UserDatabase
import com.example.pokemonapp.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class LoginEvent {
    data class CallBack(val message: String): LoginEvent()
}

class LoginViewModel(
    application: Application
): AndroidViewModel(application) {

    private val _eventChannel = Channel<LoginEvent>()
    val events = _eventChannel.receiveAsFlow()

    val driver: SqlDriver = AndroidSqliteDriver(
        UserDatabase.Schema,
        application.applicationContext,
        "user.db"
    )
    val database = UserDatabase(driver)
    val userQueries = database.userDatabaseQueries

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
            val result = userQueries.selectUser(username = username, password = password).executeAsOneOrNull()
            _eventChannel.send(
                LoginEvent.CallBack(
                    message = if (result != null) application.getString(R.string.msg_login_success, username)
                    else application.getString(R.string.msg_login_failure)
                )
            )
        }
    }
}