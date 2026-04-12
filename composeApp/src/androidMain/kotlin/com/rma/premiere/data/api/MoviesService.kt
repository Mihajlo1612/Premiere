package com.rma.premiere.data.api

import com.rma.premiere.data.model.Genre
import com.rma.premiere.data.model.Movie
import com.rma.premiere.data.model.MovieDetails
import com.rma.premiere.data.model.MovieImages
import com.rma.premiere.data.model.PaginatedResponse
import com.rma.premiere.data.model.PersonSummary
import com.rma.premiere.data.model.Video
import kotlinx.serialization.json.Json

class MoviesService(private val api: MoviesApi) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    suspend fun getMovies(
        page: Int = 1,
        pageSize: Int = 20,
        query: String? = null,
        genreId: Int? = null,
        minYear: Int? = null,
        maxYear: Int? = null,
        minRating: Float? = null,
        sortBy: String? = null,
        sortOrder: String? = null
    ): PaginatedResponse<Movie> {
        return api.getMovies(
            page = page,
            pageSize = pageSize,
            query = query,
            genreId = genreId,
            minYear = minYear,
            maxYear = maxYear,
            minRating = minRating,
            sortBy = sortBy,
            sortOrder = sortOrder
        )
    }

    suspend fun getGenres(): List<Genre> {
        return api.getGenres()
    }

    suspend fun getMovieDetails(imdbId: String): MovieDetails {
        return api.getMovieDetails(imdbId)
    }

    suspend fun getMovieImages(imdbId: String): MovieImages {
        return api.getMovieImages(imdbId)
    }

    suspend fun getMovieCast(imdbId: String): PaginatedResponse<PersonSummary> {
        return api.getMovieCast(imdbId)
    }

    suspend fun getMovieVideos(imdbId: String): List<Video> {
        return api.getMovieVideos(imdbId)
    }
}












