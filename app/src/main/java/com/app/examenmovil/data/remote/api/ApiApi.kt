package com.app.examenmovil.data.remote.api

import com.app.examenmovil.data.remote.dto.ApiDto
import com.app.examenmovil.data.remote.dto.ApiListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 172,
        @Query("offset") offset: Int = 0,
    ): ApiListDto

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: String,
    ): ApiDto
}
