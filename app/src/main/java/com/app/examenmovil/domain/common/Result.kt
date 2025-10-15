package com.app.examenmovil.domain.common
/**
 * Clase sellada (Sealed Class) utilizada para envolver el resultado de operaciones asíncronas,
 * como llamadas a la API o a la base de datos.
 *
 * Permite manejar el éxito, el error y el estado de carga de forma explícita y segura,
 * evitando el uso de excepciones para el control de flujo.
 *
 * @param T El tipo de dato que contiene el resultado en caso de éxito.
 */
sealed class Result<out T> {
    /**
     * Representa el estado inicial o cuando una operación está en curso.
     * No contiene datos.
     */
    object Loading : Result<Nothing>()
    /**
     * Representa el estado de éxito de la operación.
     *
     * @param data El dato resultante de la operación
     */
    data class Success<T>(
        val data: T,
    ) : Result<T>()
    /**
     * Representa un estado de error o fallo durante la operación.
     *
     * @param exception La excepción o Throwable que causó el fallo.
     */
    data class Error(
        val exception: Throwable,
    ) : Result<Nothing>()
}
