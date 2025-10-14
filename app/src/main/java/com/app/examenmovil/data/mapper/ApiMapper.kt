package com.app.examenmovil.data.mapper

import com.app.examenmovil.data.remote.dto.ApiDto
import com.app.examenmovil.domain.Api

fun ApiDto.toDomain(): Api =
    Api(
        id = id.toString(),
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = sprites.frontDefault,
        weight = weight,
        height = height,
        types = types.map { it.type.name },
    )
