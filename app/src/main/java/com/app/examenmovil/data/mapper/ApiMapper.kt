package com.app.examenmovil.data.mapper

import com.app.examenmovil.data.remote.dto.ApiDto
import com.app.examenmovil.domain.Api
/**
 * Extensión para convertir un ApiDto (modelo de la API) en un Api (modelo de Dominio).

 * Esta función asegura que los datos sean coherentes para la capa de Dominio,
 * manejando conversiones de tipo y la potencial nulabilidad de campos.
 *
 * @return El modelo de Dominio Api completamente inicializado.
 */
fun ApiDto.toDomain(): Api =
    Api(

        name = name.common,
        flag = flag.frontDefault,
        capital = capital?.firstOrNull()?.replaceFirstChar { it.uppercase() } ?: "N/A",
        subregion = subregion ?: "N/A",
        area = area.toInt(),
        population = population.toInt(),
        )

