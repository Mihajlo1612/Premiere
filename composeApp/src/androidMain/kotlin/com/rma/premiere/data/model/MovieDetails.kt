package com.rma.premiere.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    @SerialName("imdbId") val imdbId: String,
    @SerialName("tmdbId") val tmdbId: Int? = null,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String? = null,
    @SerialName("releaseDate") val releaseDate: String? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("budget") val budget: Long? = null,
    @SerialName("revenue") val revenue: Long? = null,
    @SerialName("languageCode") val languageCode: String? = null,
    @SerialName("popularity") val popularity: Float? = null,
    @SerialName("imdbRating") val imdbRating: Float? = null,
    @SerialName("imdbVotes") val imdbVotes: Int? = null,
    @SerialName("tmdbRating") val tmdbRating: Float? = null,
    @SerialName("posterPath") val posterPath: String? = null,
    @SerialName("backdropPath") val backdropPath: String? = null,
    @SerialName("genres") val genres: List<Genre> = emptyList()
)
