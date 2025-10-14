package com.app.examenmovil.domain.repository

import com.app.examenmovil.domain.Api

interface ApiRepository {
    suspend fun getCountryList(): List<Api>

    suspend fun getCountryByName(name: String): Api
}
