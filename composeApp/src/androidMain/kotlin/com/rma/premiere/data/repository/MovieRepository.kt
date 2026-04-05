package com.rma.premiere.data.repository

import com.rma.premiere.data.api.MoviesService
import com.rma.premiere.data.model.Movie
import com.rma.premiere.data.model.PaginatedResponse
import de.jensklingenberg.ktorfit.http.Query

class MovieRepository(private val moviesService: MoviesService) {

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
    ): Result<PaginatedResponse<Movie>> {
        return try {
            val response = moviesService.getMovies(
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
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}