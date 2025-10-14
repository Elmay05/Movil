package com.app.examenmovil.data.repository

import com.app.examenmovil.data.local.preferences.ApiPreferences
import com.app.examenmovil.data.mapper.toDomain
import com.app.examenmovil.data.remote.api.ApiApi
import com.app.examenmovil.domain.Api
import com.app.examenmovil.domain.repository.ApiRepository
import jakarta.inject.Inject

class ApiRepositoryImpl
@Inject
constructor(
    private val api: ApiApi,
    private val preferences: ApiPreferences, // AGREGADO
) : ApiRepository {
    override suspend fun getCountryList(): List<Api> {
        // Intentar obtener del caché primero
        /*preferences.getPokemonCache()?.let { cache ->
            if (preferences.isCacheValid()) {
                return cache.pokemonList
            }
        }*/

        return try {
            // Si no hay caché o expiró, obtener de la API
            val response = api.getCountryList()
            val countryList =
                response.name.map { result ->
                    val name =
                        result.common
                            .split("/")
                            .dropLast(1)
                            .last()
                    api.getCountry(name).toDomain()
                }

            /* Guardar en caché
            preferences.saveCountryList(
                countryList = countryList,
                offset = countryList.size,
                totalCount = response.count,
            )*/

            countryList
        } catch (e: Exception) {
            // Si hay error, intentar usar caché aunque haya expirado
            preferences.getCountryCache()?.let { cache ->
                return cache.countryList
            } ?: throw e
        }
    }

    override suspend fun getCountryByName(name: String): Api {
        // Intentar obtener del caché primero
        preferences.getCountryCache()?.let { cache ->
            if (preferences.isCacheValid()) {
                cache.countryList.find { it.name == name }?.let { return it }
            }
        }

        return try {
            // Si no está en caché o expiró, obtener de la API
            api.getCountry(name).toDomain()
        } catch (e: Exception) {
            // Si hay error, intentar buscar en caché aunque haya expirado
            preferences.getCountryCache()?.let { cache ->
                cache.countryList.find { it.name == name }
            } ?: throw e
        }
    }
}