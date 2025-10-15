package com.app.examenmovil.presentation.screens.home

import com.app.examenmovil.domain.Api

data class HomeUiState(
    val countryList: List<Api> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
