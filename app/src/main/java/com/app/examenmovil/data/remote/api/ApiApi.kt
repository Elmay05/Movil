package com.app.examenmovil.data.remote.api

import com.app.examenmovil.data.remote.dto.ApiDto
import com.app.examenmovil.data.remote.dto.ApiListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiApi {
    @GET("all?fields=name")
    suspend fun getCountryList(
        // 'limit' y 'offset' son innecesarios para esta API
    ):List<ApiListDto>

    @GET("name/{name}")
    suspend fun getCountry(
        @Path("name") id: String,
    ): ApiDto
}
