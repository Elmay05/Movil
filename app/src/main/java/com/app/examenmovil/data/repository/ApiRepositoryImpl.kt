package com.app.examenmovil.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.app.examenmovil.data.local.preferences.ApiPreferences
import com.app.examenmovil.data.mapper.toDomain
import com.app.examenmovil.data.remote.api.ApiApi
import com.app.examenmovil.domain.Api
import com.app.examenmovil.domain.repository.ApiRepository
import jakarta.inject.Inject
/**
 * Implementación del ApiRepository.
 *
 * Esta clase es responsable de orquestar la obtención de datos, implementando una estrategia
 * de "caché primero" para mejorar el rendimiento y reducir las llamadas a la API.
 *
 * @property api El servicio Retrofit ApiApi para la obtención de datos remotos.
 * @property preferences El manejador de preferencias ApiPreferences para la gestión de la caché local.
 */
class ApiRepositoryImpl
@Inject
constructor(
    private val api: ApiApi,
    private val preferences: ApiPreferences, // AGREGADO
) : ApiRepository {
    /**
     * Obtiene la lista completa de países, aplicando la siguiente estrategia:
     * 1. Intenta obtener la lista desde la caché si es válida.
     * 2. Si la caché no es válida o está vacía, obtiene la lista completa desde la API.
     * 3. Mapea la lista de DTOs a modelos de Dominio ([Api]) y guarda en caché.
     * 4. En caso de error de red, intenta devolver la lista desde la caché, incluso si está expirada.
     *
     * @return Una lista de objetos Api (países).
     */
    override suspend fun getCountryList(): List<Api> {
        // Intentar obtener del caché primero
        preferences.getCountryCache()?.let { cache ->
            if (preferences.isCacheValid()) {
                return cache.countryList
            }
        }

        return try {
            // Si no hay caché o expiró, obtener de la API
            val response = api.getCountryList()
            val countryList =
                response.map { result ->
                    val name =result.name.common
                    api.getCountry(name).first().toDomain()
                }

            //* Guardar en caché
            preferences.saveCountryList(
                countryList = countryList,
                offset = countryList.size,
                totalCount = response.size,
            )

            countryList
        } catch (e: Exception) {
            // Si hay error, intentar usar caché aunque haya expirado
            preferences.getCountryCache()?.let { cache ->
                return cache.countryList
            } ?: throw e
        }
    }
    /**
     * Obtiene el detalle de un país específico por su nombre.
     * Aplica la estrategia de buscar primero en la caché de la lista completa.
     *
     * 1. Busca el país en la caché si esta es válida.
     * 2. Si no se encuentra en caché o la caché expiró, realiza una llamada a la API por nombre.
     * 3. En caso de error, intenta buscar en la caché, incluso si está expirada.
     *
     * @param name El nombre del país a buscar.
     * @return Un objeto Api que contiene los detalles del país.
     */
    override suspend fun getCountryByName(name: String): Api {
        // Intentar obtener del caché primero
        preferences.getCountryCache()?.let { cache ->
            if (preferences.isCacheValid()) {
                cache.countryList.find { it.name == name }?.let { return it }
            }
        }

        return try {
            // Si no está en caché o expiró, obtener de la API
            api.getCountry(name).first().toDomain()
        } catch (e: Exception) {
            // Si hay error, intentar buscar en caché aunque haya expirado
            preferences.getCountryCache()?.let { cache ->
                cache.countryList.find { it.name == name }
            } ?: throw e
        }
    }
}