package com.app.examenmovil.presentation.screens.home.components

import CountryCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.examenmovil.domain.Api
import com.app.examenmovil.presentation.common.components.ErrorView
import com.app.examenmovil.presentation.common.components.LoadingShimmer

@OptIn(ExperimentalMaterialApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun CountryListContent(
    countryList: List<Api>,
    isLoading: Boolean,
    error: String?,
    onCountryClick: (String) -> Unit,
    onRetry: () -> Unit, // Parámetro agregado
) {
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = onRetry,
        )
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
    ) {
        when {
            isLoading -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(10) {
                        LoadingShimmer(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(160.dp),
                        )
                    }
                }
            }
            error != null && countryList.isEmpty() -> {
                ErrorView(
                    message = error,
                    onRetry = onRetry,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        items = countryList,
                        key = { it.name },
                    ) { country ->
                        CountryCard(
                            country = country,
                            onClick = { onCountryClick(country.name) },
                        )
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            scale = true,
        )
    }
}
