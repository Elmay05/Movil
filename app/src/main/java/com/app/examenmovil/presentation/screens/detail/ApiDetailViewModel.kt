package com.app.examenmovil.presentation.screens.detail
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmovil.domain.common.Result
import com.app.examenmovil.domain.usecase.GetApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiDetailViewModel
    @Inject
    constructor(
        private val getPokemonUseCase: GetApiUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ApiDetailUiState())
        val uiState: StateFlow<ApiDetailUiState> = _uiState.asStateFlow()

        fun getPokemon(id: String) {
            viewModelScope.launch {
                getPokemonUseCase(id).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )
                            is Result.Success ->
                                state.copy(
                                    pokemon = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message,
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }
    }
