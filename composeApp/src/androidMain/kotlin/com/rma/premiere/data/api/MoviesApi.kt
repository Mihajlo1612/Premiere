package com.rma.premiere.data.api

import com.rma.premiere.data.model.Genre
import com.rma.premiere.data.model.Movie
import com.rma.premiere.data.model.MovieDetails
import com.rma.premiere.data.model.MovieImages
import com.rma.premiere.data.model.PaginatedResponse
import com.rma.premiere.data.model.PersonSummary
import com.rma.premiere.data.model.Video
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface MoviesApi {

    @GET("movies")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20,
        @Query("query") query: String? = null,
        @Query("genre_id") genreId: Int? = null,
        @Query("min_year") minYear: Int? = null,
        @Query("max_year") maxYear: Int? = null,
        @Query("min_rating") minRating: Float? = null,
        @Query("sort_by") sortBy: String? = null,
        @Query("sort_order") sortOrder: String? = null,
    ): PaginatedResponse<Movie>

    @GET("genres")
    suspend fun getGenres(): List<Genre>

    @GET("movies/{id}")
    suspend fun getMovieDetails(@Path("id") imdbId: String): MovieDetails

    @GET("movies/{id}/images")
    suspend fun getMovieImages(
        @Path("id") imdbId: String,
        @Query("type") type: String = "backdrop"
    ): MovieImages

    @GET("movies/{id}/cast")
    suspend fun getMovieCast(
        @Path("id") imdbId: String,
        @Query("page_size") pageSize: Int = 10
    ): PaginatedResponse<PersonSummary>

    @GET("movies/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") imdbId: String,
        @Query("type") type: String = "Trailer"
    ): List<Video>
}