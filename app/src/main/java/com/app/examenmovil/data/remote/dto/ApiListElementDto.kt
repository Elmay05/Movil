package com.app.examenmovil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiListElementDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
