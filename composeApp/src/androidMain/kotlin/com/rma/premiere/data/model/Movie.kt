package com.rma.premiere.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("imdbId") val imdbId: String,
    @SerialName("title") val title: String,
    @SerialName("year") val year: Int? = null,
    @SerialName("imdbRating") val imdbRating: Float? = null,
    @SerialName("imdbVotes") val imdbVotes: Int? = null,
    @SerialName("posterPath") val posterPath: String? = null,
    @SerialName("genres") val genres: List<Genre> = emptyList()
)


