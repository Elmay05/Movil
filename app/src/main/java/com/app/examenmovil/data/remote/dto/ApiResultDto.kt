package com.app.examenmovil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiResultDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
