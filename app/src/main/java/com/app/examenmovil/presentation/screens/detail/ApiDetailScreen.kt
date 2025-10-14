import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.examenmovil.presentation.common.components.ErrorView
import com.app.examenmovil.presentation.common.components.LoadingShimmer
import com.app.examenmovil.presentation.screens.detail.ApiDetailViewModel
import com.app.examenmovil.presentation.screens.detail.components.PokemonDetailContent

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonId: String,
    onBackClick: () -> Unit,
    viewModel: ApiDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pokemonId) {
        viewModel.getPokemon(pokemonId)
    }

    // Usaremos un padding estándar de 16.dp para el contenedor principal
    val screenPadding = 16.dp

    // Aquí deberías tener un Scaffold si quieres una barra superior (TopAppBar)
    // Scaffold(topBar = { /* ... */ }) { paddingValues ->

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                // Usamos el padding local, no la variable rota 'padding' del import
                .padding(screenPadding),
    ) {
        when {
            uiState.isLoading -> {
                LoadingShimmer(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                )
            }
            uiState.error != null -> {
                ErrorView(
                    message = uiState.error ?: "Unknown error",
                    onRetry = { viewModel.getPokemon(pokemonId) },
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            uiState.pokemon != null -> {
                // Si el estado es exitoso, muestra el contenido detallado
                PokemonDetailContent(
                    pokemon = uiState.pokemon!!,
                )
            }
        }
    }
}
