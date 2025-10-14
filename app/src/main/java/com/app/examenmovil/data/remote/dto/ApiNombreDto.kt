package com.app.examenmovil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiNombreDto(
    @SerializedName("common") val common: String,
    @SerializedName("official") val official: String,
)
