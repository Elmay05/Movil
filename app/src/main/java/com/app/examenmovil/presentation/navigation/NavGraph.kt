package com.app.examenmovil.presentation.navigation

import CountryDetailScreen
import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
/**
 * Define las rutas de navegación para las diferentes pantallas de la aplicación.
 * Cada objeto Screen representa una ruta y, si es necesario, una función para construir
 * rutas con argumentos.
 */
sealed class Screen(
    val route: String,
) {
    object Home : Screen("home")

    object Detail : Screen("name/{countryName}") {
        /**
         * Crea la ruta completa de navegación para la pantalla de detalles.
         * @param countryName El nombre del país que se pasará como argumento.
         * @return La cadena de la ruta de navegación.
         */
        fun createRoute(countryName: String) = "name/$countryName"
    }
}
/**
 * Define la forma de navegación principal de la aplicación utilizando Compose Navigation.
 *
 * @param modifier Modificador opcional para aplicar al contenedor [NavHost].
 * @param navController Controlador de navegación para gestionar la pila de destinos. Por defecto, crea uno nuevo.
 */
@Suppress("ktlint:standard:function-naming")
@Composable
fun CountryNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onCountryClick = { countryName ->
                    navController.navigate(Screen.Detail.createRoute(countryName))
                },
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("countryName") { type = NavType.StringType }),
        ) { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: "1"
            CountryDetailScreen(
                countryName = countryName,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
