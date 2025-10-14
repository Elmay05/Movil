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

// di/AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun providePokemonApi(retrofit: Retrofit): ApiApi = retrofit.create(ApiApi::class.java)

    @Provides
    @Singleton
    fun providePokemonPreferences(
        @ApplicationContext context: Context,
        gson: Gson,
    ): ApiPreferences = ApiPreferences(context, gson)

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: ApiApi,
        preferences: ApiPreferences,
    ): ApiRepository = ApiRepositoryImpl(api, preferences)
}
