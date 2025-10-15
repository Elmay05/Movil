package com.app.examenmovil.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.app.examenmovil.data.local.model.ApiCache
import com.app.examenmovil.data.remote.dto.ApiNombreDto
import com.app.examenmovil.domain.Api
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiPreferences
@Inject
constructor(
    @ApplicationContext context: Context,
    private val gson: Gson,
) {
    /**
     * Instancia de SharedPreferences utilizada para leer y escribir los datos de la caché.
     */
    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            PreferencesConstants.PREF_NAME,
            Context.MODE_PRIVATE,
        )
    /**
     * Optimización: Define el TypeToken una sola vez para la lista de [Api].
     * Esto mejora el rendimiento al evitar la creación repetida de la clase anónima
     * durante la serialización/deserialización con Gson.
     */
    // Optimización: Definir el TypeToken una sola vez como constante.
    private val COUNTRY_LIST_TYPE: Type = object : TypeToken<List<Api>>() {}.type
    /**
     * Guarda la lista de países y sus metadatos asociados en SharedPreferences.
     *
     * @param countryList La lista de objetos de dominio Api a serializar y guardar.
     * @param offset El número de elementos cargados actualmente (para paginación).
     * @param totalCount El número total de elementos disponibles en la fuente de datos.
     */
    fun saveCountryList(
        countryList: List<Api>,
        offset: Int,
        totalCount: Int,
    ) {
        prefs
            .edit()
            .putString(PreferencesConstants.KEY_API_CACHE, gson.toJson(countryList))
            .putLong(PreferencesConstants.KEY_LAST_UPDATE, System.currentTimeMillis())
            .putInt(PreferencesConstants.KEY_OFFSET, offset)
            .putInt(PreferencesConstants.KEY_TOTAL_COUNT, totalCount)
            .apply()
    }
    /**
     * Recupera la caché de países completa, incluyendo la lista de países y los metadatos.
     *
     * @return Un objeto ApiCache que contiene los datos, o `null` si no hay datos guardados.
     */
    fun getCountryCache(): ApiCache? {
        val json = prefs.getString(PreferencesConstants.KEY_API_CACHE, null)
        val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0)
        val offset = prefs.getInt(PreferencesConstants.KEY_OFFSET, 0)
        val totalCount = prefs.getInt(PreferencesConstants.KEY_TOTAL_COUNT, 0)

        if (json == null) return null

        // Usamos la constante definida arriba
        val countryList: List<Api> = gson.fromJson(json, COUNTRY_LIST_TYPE)

        return ApiCache(
            countryList = countryList,
            lastUpdate = lastUpdate,
            offset = offset,
            totalCount = totalCount,
        )
    }

    fun isCacheValid(): Boolean {
        val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0)
        return System.currentTimeMillis() - lastUpdate < PreferencesConstants.CACHE_DURATION
    }

    fun clearCache() {
        prefs.edit().clear().apply()
    }
}
