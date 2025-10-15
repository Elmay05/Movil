package com.app.examenmovil.domain

data class Api(
    val name: String,
    val flag: String,
    val capital: String,
    val subregion: String,
    val area: Int,
    val population: Int,
) {
    companion object {
        fun getMockData(): List<Api> =
            listOf(
                Api(
                    name = "Chile",
                    flag = "https://flagcdn.com/w320/cl.png",
                    capital = "Santiago",
                    subregion = "South America",
                    area = 756102,
                    population = 19116209,
                ),
                Api(
                    name = "Mexico",
                    flag = "https://flagcdn.com/w320/mx.png",
                    capital = "Mexico City",
                    subregion = "North America",
                    area = 1964375,
                    population = 128932753,
                ),
                // Agregar más Pokémon mock...
            )
    }
}
