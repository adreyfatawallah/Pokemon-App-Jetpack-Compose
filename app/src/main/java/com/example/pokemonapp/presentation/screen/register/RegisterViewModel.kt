package com.example.pokemonapp.presentation.screen.register

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.UserDatabase

class RegisterViewModel(
    application: Application
): AndroidViewModel(application) {

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
    var retypePassword by mutableStateOf("")
        private set

    var isUsernameValid by mutableStateOf(false)
        private set
    var isPasswordValid by mutableStateOf(false)
        private set
    var isPasswordMatch by mutableStateOf(false)
        private set

    var errorRegister: String? by mutableStateOf(null)
        private set

    fun updateUsername(string: String) {
        username = string
        isUsernameValid = username.length > 4
    }

    fun updatePassword(string: String) {
        password = string
        isPasswordValid = password.length > 4
        isPasswordMatch = password == retypePassword
    }

    fun updateRetypePassword(string: String) {
        retypePassword = string
        isPasswordMatch = password == retypePassword
    }

    suspend fun register(): Boolean {
        try {
            val result = userQueries.insertUser(username = username, password = password).await()
            return result > 0
        } catch (e: Exception) {
            errorRegister = e.message
            return false
        }
    }
}