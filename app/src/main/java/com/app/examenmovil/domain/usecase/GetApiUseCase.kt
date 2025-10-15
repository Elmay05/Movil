package com.app.examenmovil.domain.usecase
import com.app.examenmovil.domain.Api
import com.app.examenmovil.domain.common.Result
import com.app.examenmovil.domain.repository.ApiRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Caso de Uso (Use Case) para obtener
 * el detalle de un país específico por su nombre.
 *
 * Implementa el patrón `invoke` (operador) para ser llamado directamente
 * como una función (e.g., `getCountryUseCase(name)`).
 *
 * @property repository La interfaz del repositorio que proporciona la fuente de datos.
 */
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
