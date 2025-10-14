package com.app.examenmovil.domain.repository

import com.app.examenmovil.domain.Api

interface ApiRepository {
    suspend fun getPokemonList(): List<Api>

    suspend fun getPokemonById(id: String): Api
}
