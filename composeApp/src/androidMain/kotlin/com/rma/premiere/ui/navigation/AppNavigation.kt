package com.rma.premiere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import com.rma.premiere.ui.screens.MoviesListScreen
import com.rma.premiere.ui.screens.FilterMoviesScreen

@Serializable
object MoviesListRoute

@Serializable
object FilterMoviesRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MoviesListRoute
    ) {
        composable<MoviesListRoute> { backStackEntry ->
            val query = backStackEntry.savedStateHandle.getStateFlow<String?>("query", null)
                .collectAsState()
            val genreId = backStackEntry.savedStateHandle.getStateFlow<Int?>("genreId", null)
            val minYear = backStackEntry.savedStateHandle.getStateFlow<Int?>("minYear", null)
            val maxYear = backStackEntry.savedStateHandle.getStateFlow<Int?>("maxYear", null)
            val minRating = backStackEntry.savedStateHandle.getStateFlow<Float?>("minRating", null)

            MoviesListScreen(
                onFilterClick = { navController.navigate(FilterMoviesRoute) },
                query = query.value,
                genreId = genreId.value,
                minYear = minYear.value,
                maxYear = maxYear.value,
                minRating = minRating.value
            )
        }
        composable<FilterMoviesRoute> {
            FilterMoviesScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onApplyFilters = { query, genreId, minYear, maxYear, minRating ->
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("query", query)
                        set("genreId", genreId)
                        set("minYear", minYear)
                        set("maxYear", maxYear)
                        set("minRating", minRating)
                    }
                    navController.popBackStack()
                }

            )
        }
    }
}