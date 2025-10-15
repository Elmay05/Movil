package com.app.examenmovil.data.remote.api

import com.app.examenmovil.data.remote.dto.ApiDto
import com.app.examenmovil.data.remote.dto.ApiListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
/**
 * Interfaz de servicio de Retrofit para interactuar con la API REST Countries.
 *
 * Define los contratos de las llamadas HTTP GET para obtener listas y detalles de países.
 */
interface ApiApi {
    /**
     * Obtiene una lista parcial de países, incluyendo solo el campo 'name' para una búsqueda rápida.
     *
     * Endpoint: GET https://restcountries.com/v3.1/all?fields=name
     *
     * @return Una lista de objetos ApiListDto con datos básicos (solo el nombre).
     */
    @GET("all?fields=name")
    suspend fun getCountryList(
        // 'limit' y 'offset' son innecesarios para esta API
    ):List<ApiListDto>
    /**
     * Obtiene el detalle completo de un país específico por su nombre.
     *
     * Endpoint: GET https://restcountries.com/v3.1/name/{name}
     *
     * @param name El nombre oficial o común del país (inyectado en el Path).
     * @return Una lista de objetos ApiDto que representan el detalle del país.
     * (Nota: La API de países devuelve una lista, incluso para una búsqueda por nombre único).
     */
    @GET("name/{name}")
    suspend fun getCountry(
        @Path("name") name: String,
    ): List<ApiDto>
}
