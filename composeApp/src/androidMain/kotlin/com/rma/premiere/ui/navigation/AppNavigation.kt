package com.rma.premiere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import com.rma.premiere.ui.screens.MoviesListScreen
import com.rma.premiere.ui.screens.FilterMoviesScreen
import com.rma.premiere.ui.screens.MovieDetaisScreen

@Serializable
object MoviesListRoute

@Serializable
object FilterMoviesRoute

@Serializable
data class MovieDetailsRoute(val imdbId: String)

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
            val genreId =
                backStackEntry.savedStateHandle.getStateFlow<Int?>("genreId", null).collectAsState()
            val minYear =
                backStackEntry.savedStateHandle.getStateFlow<Int?>("minYear", null).collectAsState()
            val maxYear =
                backStackEntry.savedStateHandle.getStateFlow<Int?>("maxYear", null).collectAsState()
            val minRating = backStackEntry.savedStateHandle.getStateFlow<Float?>("minRating", null)
                .collectAsState()

            MoviesListScreen(
                onFilterClick = { navController.navigate(FilterMoviesRoute) },
                onMovieClick = { imdbId -> navController.navigate(MovieDetailsRoute(imdbId)) },
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
        composable<MovieDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<MovieDetailsRoute>()
            MovieDetaisScreen(
                imdbId = route.imdbId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}