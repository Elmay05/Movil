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

/**
 * ViewModel para gestionar estado de la pantalla de detalles de un país.
 * Utiliza GetApiUseCase para obtener los datos detallados de un país específico.
 */
@HiltViewModel
class ApiDetailViewModel
    @Inject
    constructor(
        private val getCountryUseCase: GetApiUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ApiDetailUiState())
        val uiState: StateFlow<ApiDetailUiState> = _uiState.asStateFlow()
    /**
     * Inicia la carga de los detalles de un país.
     * Trabaja los estados loading, success y error al llamar a getCountryUseCase
     *
     * @param name El nombre del país que se desea buscar y obtener los detalles.
     */
        fun getCountry(name: String) {
            viewModelScope.launch {
                getCountryUseCase(name).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )
                            is Result.Success ->
                                state.copy(
                                    country = result.data,
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
