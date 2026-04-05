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
            MoviesListScreen(
                onFilterClick = { navController.navigate(FilterMoviesRoute) },
                query = query.value
            )
        }
        composable<FilterMoviesRoute> {
            FilterMoviesScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onApplyFilters = { query ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("query", query)
                    navController.popBackStack()
                }

            )
        }
    }
}