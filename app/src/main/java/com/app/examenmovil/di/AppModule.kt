package com.app.examenmovil.di

import android.content.Context
import com.app.examenmovil.data.local.preferences.ApiPreferences
import com.app.examenmovil.data.remote.api.ApiApi
import com.app.examenmovil.data.repository.ApiRepositoryImpl
import com.app.examenmovil.domain.repository.ApiRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
/**
 * Este módulo es responsable de crear y configurar instancias de las dependencias
 * principales de la aplicación, como Retrofit, Gson, Repositorios y Preferencias.
 */
// di/AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Proporciona una instancia Singleton de Retrofit.
     * Configura el URL base y el convertidor Gson para la deserialización JSON.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    /**
     * Proporciona una instancia Singleton de Gson, utilizada para la serialización/deserialización
     * de objetos, incluyendo el manejo de caché.
     */
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
    /**
     * Proporciona la implementación de la interfaz ApiApi (Servicio API).
     *
     * @param retrofit La instancia Singleton de Retrofit.
     */
    @Provides
    @Singleton
    fun providePokemonApi(retrofit: Retrofit): ApiApi = retrofit.create(ApiApi::class.java)
    /**
     * Proporciona una instancia Singleton de [ApiPreferences], el manejador de caché local.
     *
     * @param context El contexto de la aplicación, inyectado por Hilt.
     * @param gson La instancia Singleton de Gson.
     */
    @Provides
    @Singleton
    fun providePokemonPreferences(
        @ApplicationContext context: Context,
        gson: Gson,
    ): ApiPreferences = ApiPreferences(context, gson)
    /**
     * Proporciona la implementación concreta [ApiRepositoryImpl] como la interfaz [ApiRepository].
     * Esto permite que la capa de Dominio dependa de la abstracción.
     *
     * @param api La instancia del servicio API.
     * @param preferences El manejador de preferencias/caché.
     * @return La implementación del repositorio.
     */
    @Provides
    @Singleton
    fun providePokemonRepository(
        api: ApiApi,
        preferences: ApiPreferences,
    ): ApiRepository = ApiRepositoryImpl(api, preferences)
}
