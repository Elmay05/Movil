package com.app.examenmovil.presentation.screens.detail

import com.app.examenmovil.domain.Api

data class ApiDetailUiState(
    val pokemon: Api? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
