package com.rma.premiere.ui.screens

import com.rma.premiere.data.model.Movie
import com.rma.premiere.ui.base.UiState


data class MoviesListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null,
    val totalMovies: Int = 0
) : UiState
