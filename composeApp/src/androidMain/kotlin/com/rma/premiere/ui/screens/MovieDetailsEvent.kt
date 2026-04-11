package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.UiEvent

sealed class MovieDetailsEvent : UiEvent {
    data class LoadMovieDetails(val imdbId: String) : MovieDetailsEvent()
    object OnRetry : MovieDetailsEvent()
    object OnPlayTrailer : MovieDetailsEvent()
}