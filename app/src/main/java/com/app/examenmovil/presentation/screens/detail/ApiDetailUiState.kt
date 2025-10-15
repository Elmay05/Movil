package com.app.examenmovil.presentation.screens.detail

import com.app.examenmovil.domain.Api
/**
 * Data class que representa el estado de la UI para la pantalla de detalles de un país.
 * para renderizar la información, estados de carga y errores.
 *
 * @property country El modelo de domain Api que contiene los datos del país a mostrar,
 * o `null` si aún no se ha cargado.
 * @property isLoading Booleano que indica si se espera la respuesta de la API.
 * @property error Mensaje de error a mostrar al usuario si la carga de datos falla, o `null` si
 *
 */
data class ApiDetailUiState(
    val country: Api? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
