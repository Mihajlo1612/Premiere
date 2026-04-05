package com.rma.premiere.ui.screens

import androidx.lifecycle.viewModelScope
import com.rma.premiere.data.repository.MovieRepository
import com.rma.premiere.ui.base.BaseViewModel
import io.ktor.utils.io.core.ByteOrder
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
        }
    }

    private fun loadMovies(
        sortBy: String = uiState.value.sortBy,
        sortOrder: String = uiState.value.sortOrder,
        query: String = uiState.value.query
    ) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            repository.getMovies(
                sortBy = sortBy,
                sortOrder = sortOrder,
                query = query.ifEmpty { null }
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