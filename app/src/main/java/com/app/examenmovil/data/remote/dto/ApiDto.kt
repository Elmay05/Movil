package com.app.examenmovil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiDto(

    // **CORRECCIÓN 1: 'name' es un objeto complejo en la API, no un String.**
    @SerializedName("name") val name: NameDto,
    @SerializedName("flags") val flag: SpritesDto,

    // **CORRECCIÓN 2: 'capital' es una lista de Strings en la API, no un String simple.**
    @SerializedName("capital") val capital: List<String>?,

    @SerializedName("subregion") val subregion: String?, // Añadido '?' para opcionales
    @SerializedName("area") val area: Double, // Cambiado a Double ya que el área puede ser grande
    @SerializedName("population") val population: Long, // Cambiado a Long por si la población es grande
) {
    // Nueva clase para mapear el objeto 'name' de la API
    data class NameDto(
        @SerializedName("common") val common: String,
        @SerializedName("official") val official: String,
    )

    data class SpritesDto(
        @SerializedName("png") val frontDefault: String,
    )
}