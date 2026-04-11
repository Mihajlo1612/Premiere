package com.rma.premiere.ui.screens

import androidx.lifecycle.viewModelScope
import com.rma.premiere.data.repository.MovieRepository
import com.rma.premiere.ui.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository
) : BaseViewModel<MovieDetailsState, MovieDetailsEvent>() {

    override fun initialState() = MovieDetailsState()

    private var currentImdbId: String? = null

    override fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.LoadMovieDetails -> {
                currentImdbId = event.imdbId
                loadDetails(event.imdbId)
            }

            is MovieDetailsEvent.OnRetry -> {
                currentImdbId?.let { loadDetails(it) }
            }

            is MovieDetailsEvent.OnPlayTrailer -> {}
        }
    }

    private fun loadDetails(imdbId: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            val detailsDeffered = async { repository.getMovieDetails(imdbId) }
            val imagesDeffered = async { repository.getMovieImages(imdbId) }
            val castDeffered = async { repository.getMovieCast(imdbId) }
            val videosDeffered = async { repository.getMovieVideos(imdbId) }

            val details = detailsDeffered.await()
            val images = imagesDeffered.await()
            val cast = castDeffered.await()
            val videos = videosDeffered.await()
            println("VIDEOS_DEBUG: ${videos.getOrNull()?.size} videa, greška: ${videos.exceptionOrNull()?.message}")

            if (details.isFailure) {
                setState {
                    copy(
                        isLoading = false,
                        error = details.exceptionOrNull()?.message ?: "Unknown error"
                    )
                }
                return@launch
            }

            setState {
                copy(
                    isLoading = false,
                    movie = details.getOrNull(),
                    backdrops = images.getOrNull()?.backdrops?.take(6) ?: emptyList(),
                    cast = cast.getOrNull() ?: emptyList(),
                    trailerKey = videos.getOrNull()?.firstOrNull() { it.official }?.key
                        ?: videos.getOrNull()?.firstOrNull()?.key
                )
            }

        }
    }
}