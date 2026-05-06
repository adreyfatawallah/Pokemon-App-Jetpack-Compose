package com.example.pokemonapp.main.domain

import com.example.pokemonapp.config.preference.MyPreference
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val preference: MyPreference
): MainRepository {

    override fun hasLogin(): Flow<Boolean> {
        return preference.hasLogin
    }
}