package com.app.examenmovil.data.mapper

import com.app.examenmovil.data.remote.dto.ApiDto
import com.app.examenmovil.domain.Api

fun ApiDto.toDomain(): Api =
    Api(
        name = name.replaceFirstChar { it.uppercase() },
        flag = flag.frontDefault,
        capital = capital.replaceFirstChar { it.uppercase() },
        subregion = subregion.replaceFirstChar { it.uppercase() },
        area = area,
        population = population,
    )
