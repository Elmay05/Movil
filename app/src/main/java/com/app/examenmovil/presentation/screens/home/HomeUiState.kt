package com.app.examenmovil.presentation.screens.home

import com.app.examenmovil.domain.Api

data class HomeUiState(
    val pokemonList: List<Api> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
