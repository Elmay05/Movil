package com.app.examenmovil.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiListDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<ApiResultDto>,
)
