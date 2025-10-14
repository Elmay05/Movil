package com.app.examenmovil.domain.usecase
import com.app.examenmovil.domain.Api
import com.app.examenmovil.domain.common.Result
import com.app.examenmovil.domain.repository.ApiRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetApiUseCase
    @Inject
    constructor(
        private val repository: ApiRepository,
    ) {
        operator fun invoke(id: String): Flow<Result<Api>> =
            flow {
                try {
                    emit(Result.Loading)
                    val pokemon = repository.getPokemonById(id)
                    emit(Result.Success(pokemon))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
