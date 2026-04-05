package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.UiEvent

sealed class MoviesListEvent : UiEvent {
    object LoadMovies : MoviesListEvent()
    data class OnSortChanged(val sortBy: String, val sortOrder: String) : MoviesListEvent()

    data class OnQueryChanged(val query: String) : MoviesListEvent()
}