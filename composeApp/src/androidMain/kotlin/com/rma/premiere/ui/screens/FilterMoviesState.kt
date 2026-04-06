package com.rma.premiere.ui.screens

import com.rma.premiere.data.model.Genre
import com.rma.premiere.ui.base.UiState

data class FilterMoviesState(
    val query: String = "",
    val genres: List<Genre> = emptyList(),
    val selectedGenreId: Int? = null,
    val minYear: Int = 1920,
    val maxYear: Int = 2025,
    val minRating: Float = 0f,
    val isLoading: Boolean = false,
    val error: String? = null
) : UiState
