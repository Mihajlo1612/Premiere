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
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            repository.getMovies().fold(
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