package com.rma.premiere.ui.screens

import androidx.lifecycle.viewModelScope
import com.rma.premiere.data.repository.MovieRepository
import com.rma.premiere.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val repository: MovieRepository
) : BaseViewModel<MoviesListState, MoviesListEvent>() {

    override fun initialState() = MoviesListState()

    init {
        onEvent(MoviesListEvent.LoadMovies)
    }

    override fun onEvent(event: MoviesListEvent) {
        when (event) {
            is MoviesListEvent.LoadMovies -> loadMovies()
            is MoviesListEvent.OnSortChanged -> {
                setState { copy(sortBy = event.sortBy, sortOrder = event.sortOrder) }
                loadMovies(sortBy = event.sortBy, sortOrder = event.sortOrder)
            }

            is MoviesListEvent.OnQueryChanged -> {
                setState { copy(query = event.query) }
                loadMovies(query = event.query)
            }

            is MoviesListEvent.OnFiltersApplied -> {
                setState {
                    copy(
                        query = event.query ?: "",
                        genreId = event.genreId,
                        minYear = event.minYear,
                        maxYear = event.maxYear,
                        minRating = event.minRating
                    )
                }
                loadMovies(
                    query = event.query ?: "",
                    genreId = event.genreId,
                    minYear = event.minYear,
                    maxYear = event.maxYear,
                    minRating = event.minRating
                )
            }
        }
    }

    private fun loadMovies(
        sortBy: String = uiState.value.sortBy,
        sortOrder: String = uiState.value.sortOrder,
        query: String = uiState.value.query,
        genreId: Int? = uiState.value.genreId,
        minYear: Int? = uiState.value.minYear,
        maxYear: Int? = uiState.value.maxYear,
        minRating: Float? = uiState.value.minRating
    ) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            repository.getMovies(
                sortBy = sortBy,
                sortOrder = sortOrder,
                query = query.ifEmpty { null },
                genreId = genreId,
                minYear = minYear,
                maxYear = maxYear,
                minRating = minRating
            ).fold(
                onSuccess = { response ->
                    setState {
                        copy(
                            isLoading = false,
                            movies = response.items,
                            totalMovies = response.totalItems
                        )
                    }
                },
                onFailure = { error ->
                    setState {
                        copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }
}