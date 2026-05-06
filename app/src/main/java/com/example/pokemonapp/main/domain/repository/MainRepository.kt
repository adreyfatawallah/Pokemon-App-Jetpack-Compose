package com.example.pokemonapp.main.domain.repository

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun hasLogin() : Flow<Boolean>
}