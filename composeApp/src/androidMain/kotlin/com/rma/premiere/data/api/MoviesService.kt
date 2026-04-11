package com.rma.premiere.data.api

import com.rma.premiere.data.model.Genre
import com.rma.premiere.data.model.Movie
import com.rma.premiere.data.model.MovieDetails
import com.rma.premiere.data.model.MovieImages
import com.rma.premiere.data.model.PaginatedResponse
import com.rma.premiere.data.model.PersonSummary
import com.rma.premiere.data.model.Video
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class MoviesService(private val client: HttpClient) {

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
        return client.get("https://rma.finlab.rs/movies") {
            parameter("page", page)
            parameter("page_size", pageSize)
            query?.let { parameter("query", it) }
            genreId?.let { parameter("genre_id", it) }
            minYear?.let { parameter("min_year", it) }
            maxYear?.let { parameter("max_year", it) }
            minRating?.let { parameter("min_rating", it) }
            sortBy?.let { parameter("sort_by", it) }
            sortOrder?.let { parameter("sort_order", it) }
        }.body()
    }

    suspend fun getGenres(): List<Genre> {
        return client.get("https://rma.finlab.rs/genres").body()
    }

    suspend fun getMovieDetails(imdbId: String): MovieDetails {
        return client.get("https://rma.finlab.rs/movies/$imdbId").body()
    }

    suspend fun getMovieImages(imdbId: String): MovieImages {
        return client.get("https://rma.finlab.rs/movies/$imdbId/images") {
            parameter("type", "backdrop")
        }.body()
    }

    suspend fun getMovieCast(imdbId: String): PaginatedResponse<PersonSummary> {
        val response = client.get("https://rma.finlab.rs/movies/$imdbId/cast") {
            parameter("page_size", 10)
        }
        return json.decodeFromString(response.bodyAsText())
    }

    suspend fun getMovieVideos(imdbId: String): List<Video> {
        val response = client.get("https://rma.finlab.rs/movies/$imdbId/videos") {
            parameter("type", "Trailer")
        }
        return json.decodeFromString(response.bodyAsText())
    }
}












