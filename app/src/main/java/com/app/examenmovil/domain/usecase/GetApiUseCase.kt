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
        operator fun invoke(name: String): Flow<Result<Api>> =
            flow {
                try {
                    emit(Result.Loading)
                    val country = repository.getCountryByName(name)
                    emit(Result.Success(country))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
