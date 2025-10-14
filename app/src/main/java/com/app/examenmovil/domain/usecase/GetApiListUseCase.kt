package com.app.examenmovil.domain.usecase
import com.app.examenmovil.domain.Api
import com.app.examenmovil.domain.common.Result
import com.app.examenmovil.domain.repository.ApiRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetApiListUseCase
    @Inject
    constructor(
        private val repository: ApiRepository,
    ) {
        operator fun invoke(): Flow<Result<List<Api>>> =
            flow {
                try {
                    emit(Result.Loading)
                    val pokemonList = repository.getPokemonList()
                    emit(Result.Success(pokemonList))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
