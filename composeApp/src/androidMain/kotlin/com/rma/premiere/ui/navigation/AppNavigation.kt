package com.rma.premiere.ui.navigation

import androidx.compose.runtime.Composable
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
        composable<MoviesListRoute> {
            MoviesListScreen(
                onFilterClick = {
                    navController.navigate(FilterMoviesRoute)
                }
            )
        }
        composable<FilterMoviesRoute> {
            FilterMoviesScreen(
                onBackClick = {
                    navController.popBackStack()
                }

            )
        }
    }
}