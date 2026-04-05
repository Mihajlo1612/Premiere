package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.UiState

data class FilterMoviesState(
    val query: String = "",
    val selectedGenres: Set<Int> = emptySet(),
    val minYear: Int = 1920,
    val maxYear: Int = 2025,
    val minRating: Float = 0f
) : UiState
