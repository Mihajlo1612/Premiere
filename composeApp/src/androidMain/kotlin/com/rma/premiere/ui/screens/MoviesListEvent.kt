package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.UiEvent

sealed class MoviesListEvent : UiEvent {
    object LoadMovies : MoviesListEvent()
}