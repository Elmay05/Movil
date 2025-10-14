package com.app.examenmovil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiDto(

    @SerializedName("name") val name: String,
    @SerializedName("flags") val flag: SpritesDto,
    @SerializedName("capital") val capital: String,
    @SerializedName("subregion") val subregion: String,
    @SerializedName("area") val area: Int,
    @SerializedName("population") val population: Int,
) {
    data class SpritesDto(
        @SerializedName("png") val frontDefault: String,
    )
}
