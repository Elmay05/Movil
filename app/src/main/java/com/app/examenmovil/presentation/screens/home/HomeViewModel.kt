package com.app.examenmovil.presentation.screens.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenmovil.domain.common.Result
import com.app.examenmovil.domain.usecase.GetApiListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getCountryListUseCase: GetApiListUseCase,
    ) : ViewModel() {
        // Para guardar el valor de las variables
        private val _uiState = MutableStateFlow(HomeUiState())

        // Las screen no reciben mutables, por buena practica, se guarda en una inmutable
        val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

        // sirve para iniciar la funcion de abajo
        init {
            loadCountryList()
        }

        fun loadCountryList() {
            // viewModleScope
            viewModelScope.launch {
                // usa nuestro caso de uso
                getCountryListUseCase().collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )
                            is Result.Success ->
                                state.copy(
                                    countryList = result.data,
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
