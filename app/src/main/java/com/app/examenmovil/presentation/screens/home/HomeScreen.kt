import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.examenmovil.presentation.screens.home.HomeViewModel
import com.app.examenmovil.presentation.screens.home.components.CountryListContent
import com.app.examenmovil.presentation.screens.search.SearchTab


@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCountryClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Country List", "Search")
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Countries") },
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                    )
                }
            }

            when (selectedTabIndex) {
                0 ->
                   CountryListContent(
                        countryList = uiState.countryList,
                        isLoading = uiState.isLoading,
                        error = uiState.error,
                        onCountryClick = onCountryClick,
                        onRetry = { viewModel.loadCountryList() }, // Parámetro agregado
                    )
                1 -> SearchTab(onCountryClick = onCountryClick)
            }
        }
    }
}
