package com.app.examenmovil.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.app.examenmovil.data.local.model.ApiCache
import com.app.examenmovil.data.remote.dto.ApiNombreDto
import com.app.examenmovil.domain.Api
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiPreferences
    @Inject
    constructor(
        @ApplicationContext context: Context,
        private val gson: Gson,
    ) {
        private val prefs: SharedPreferences =
            context.getSharedPreferences(
                PreferencesConstants.PREF_NAME,
                Context.MODE_PRIVATE,
            )

        fun saveCountryList(
            countryList: List<Api>,
            offset: Int,
            totalCount: List<ApiNombreDto>,
        ) {
            prefs
                .edit()
                .putString(PreferencesConstants.KEY_API_CACHE, gson.toJson(countryList))
                .putLong(PreferencesConstants.KEY_LAST_UPDATE, System.currentTimeMillis())
                .putInt(PreferencesConstants.KEY_OFFSET, offset)
                .putInt(PreferencesConstants.KEY_TOTAL_COUNT, totalCount.size)
                .apply()
        }

        fun getCountryCache(): ApiCache? {
            val json = prefs.getString(PreferencesConstants.KEY_API_CACHE, null)
            val lastUpdate = prefs.getLong(PreferencesConstants.KEY_LAST_UPDATE, 0)
            val offset = prefs.getInt(PreferencesConstants.KEY_OFFSET, 0)
            val totalCount = prefs.getInt(PreferencesConstants.KEY_TOTAL_COUNT, 0)

            if (json == null) return null

            val type = object : TypeToken<List<Api>>() {}.type
            val countryList: List<Api> = gson.fromJson(json, type)

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
