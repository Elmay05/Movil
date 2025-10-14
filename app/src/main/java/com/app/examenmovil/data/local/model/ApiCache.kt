package com.app.examenmovil.data.local.model

import com.app.examenmovil.domain.Api

data class ApiCache(
    val pokemonList: List<Api>,
    val lastUpdate: Long,
    val offset: Int,
    val totalCount: Int,
)
