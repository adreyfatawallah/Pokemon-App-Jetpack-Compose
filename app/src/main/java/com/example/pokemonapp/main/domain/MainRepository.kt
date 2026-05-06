package com.example.pokemonapp.main.domain

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun hasLogin() : Flow<Boolean>
}