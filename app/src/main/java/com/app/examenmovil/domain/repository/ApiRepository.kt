package com.app.examenmovil.domain.repository

import com.app.examenmovil.domain.Api
/**

 * Abstrae la fuente de datos (API remota, caché local) de la capa de Domain,
 * garantizando que la capa de negocio solo interactúe con el modelo [Api].
 */
interface ApiRepository {
    /**
     * Obtiene la lista completa de países disponibles.
     * La implementación debe decidir si obtiene la información de la caché o de la API remota.
     *
     * @return Una lista de objetos Api (países).
     */
    suspend fun getCountryList(): List<Api>
    /**
     * Obtiene el detalle de un país específico por su nombre.
     *
     * @param name El nombre del país a buscar.
     * @return Un objeto Api que contiene los detalles del país.
     */
    suspend fun getCountryByName(name: String): Api
}
