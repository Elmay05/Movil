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
        override suspend fun getPokemonList(): List<Api> {
            // Intentar obtener del caché primero
            preferences.getPokemonCache()?.let { cache ->
                if (preferences.isCacheValid()) {
                    return cache.pokemonList
                }
            }

            return try {
                // Si no hay caché o expiró, obtener de la API
                val response = api.getPokemonList()
                val pokemonList =
                    response.results.map { result ->
                        val id =
                            result.url
                                .split("/")
                                .dropLast(1)
                                .last()
                        api.getPokemon(id).toDomain()
                    }

                // Guardar en caché
                preferences.savePokemonList(
                    pokemonList = pokemonList,
                    offset = pokemonList.size,
                    totalCount = response.count,
                )

                pokemonList
            } catch (e: Exception) {
                // Si hay error, intentar usar caché aunque haya expirado
                preferences.getPokemonCache()?.let { cache ->
                    return cache.pokemonList
                } ?: throw e
            }
        }

        override suspend fun getPokemonById(id: String): Api {
            // Intentar obtener del caché primero
            preferences.getPokemonCache()?.let { cache ->
                if (preferences.isCacheValid()) {
                    cache.pokemonList.find { it.id == id }?.let { return it }
                }
            }

            return try {
                // Si no está en caché o expiró, obtener de la API
                api.getPokemon(id).toDomain()
            } catch (e: Exception) {
                // Si hay error, intentar buscar en caché aunque haya expirado
                preferences.getPokemonCache()?.let { cache ->
                    cache.pokemonList.find { it.id == id }
                } ?: throw e
            }
        }
    }
