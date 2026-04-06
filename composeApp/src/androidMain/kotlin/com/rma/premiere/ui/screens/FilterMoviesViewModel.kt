package com.rma.premiere.ui.screens

import androidx.lifecycle.viewModelScope
import com.rma.premiere.data.repository.MovieRepository
import com.rma.premiere.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FilterMoviesViewModel(
    private val repository: MovieRepository
) : BaseViewModel<FilterMoviesState, FilterMoviesEvent>() {

    override fun initialState() = FilterMoviesState()

    init {
        onEvent(FilterMoviesEvent.LoadGenres)
    }

    override fun onEvent(event: FilterMoviesEvent) {
        when (event) {
            is FilterMoviesEvent.LoadGenres -> loadGenres()
            is FilterMoviesEvent.OnGenreSelected -> setState { copy(selectedGenreId = event.genreId) }
            is FilterMoviesEvent.OnMinYearChanged -> setState { copy(minYear = event.year) }
            is FilterMoviesEvent.OnMaxYearChanged -> setState { copy(maxYear = event.year) }
            is FilterMoviesEvent.OnMinRatingChanged -> setState { copy(minRating = event.rating) }
            is FilterMoviesEvent.OnQueryChanged -> {
                setState { copy(query = event.query) }
            }

            is FilterMoviesEvent.OnApplyFilters -> {}
            is FilterMoviesEvent.OnClearAll -> {
                setState { copy(query = "") }
            }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            repository.getGenres().fold(
                onSuccess = { genres ->
                    setState { copy(isLoading = false, genres = genres) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, error = error.message) }
                }
            )
        }
    }
}