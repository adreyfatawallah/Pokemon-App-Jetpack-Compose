package com.example.pokemonapp.config.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")

class UserPreference(
    private val context: Context
) {
    private object PreferenceKey {
        val HAS_LOGIN = booleanPreferencesKey("has_login")
        val USERNAME = stringPreferencesKey("username")
    }

    val hasLogin: Flow<Boolean> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preference ->
            preference[PreferenceKey.HAS_LOGIN] ?: false
        }
        .distinctUntilChanged()

    suspend fun updateStatusLogin(login: Boolean) {
        context.dataStore.edit { preference ->
            preference[PreferenceKey.HAS_LOGIN] = login
        }
    }

    val username: Flow<String> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preference ->
            preference[PreferenceKey.USERNAME] ?: ""
        }
        .distinctUntilChanged()

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preference ->
            preference[PreferenceKey.USERNAME] = username
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }
}