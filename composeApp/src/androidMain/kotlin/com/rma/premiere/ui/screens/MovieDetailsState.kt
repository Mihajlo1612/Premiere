package com.rma.premiere.ui.screens

import com.rma.premiere.data.model.MovieDetails
import com.rma.premiere.data.model.MovieImage
import com.rma.premiere.data.model.PersonSummary
import com.rma.premiere.ui.base.UiState

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movie: MovieDetails? = null,
    val backdrops: List<MovieImage> = emptyList(),
    val cast: List<PersonSummary> = emptyList(),
    val trailerKey: String? = null,
    val error: String? = null
) : UiState
