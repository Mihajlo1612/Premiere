package com.rma.premiere.ui.screens

import com.rma.premiere.ui.base.UiEvent

sealed class FilterMoviesEvent : UiEvent {

    object LoadGenres : FilterMoviesEvent()
    data class OnQueryChanged(val query: String) : FilterMoviesEvent()
    data class OnGenreSelected(val genreId: Int?) : FilterMoviesEvent()
    data class OnMinYearChanged(val year: Int) : FilterMoviesEvent()
    data class OnMaxYearChanged(val year: Int) : FilterMoviesEvent()
    data class OnMinRatingChanged(val rating: Float) : FilterMoviesEvent()
    object OnApplyFilters : FilterMoviesEvent()
    object OnClearAll : FilterMoviesEvent()

}