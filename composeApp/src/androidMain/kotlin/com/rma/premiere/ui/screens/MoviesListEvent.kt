package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.UiEvent

sealed class MoviesListEvent : UiEvent {
    object LoadMovies : MoviesListEvent()
    data class OnSortChanged(val sortBy: String, val sortOrder: String) : MoviesListEvent()
    data class OnQueryChanged(val query: String) : MoviesListEvent()
    data class OnFiltersApplied(
        val query: String? = null,
        val genreId: Int? = null,
        val minYear: Int? = null,
        val maxYear: Int? = null,
        val minRating: Float? = null
    ) : MoviesListEvent()
}