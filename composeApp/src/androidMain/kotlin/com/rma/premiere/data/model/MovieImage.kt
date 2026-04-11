package com.rma.premiere.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieImages(
    @SerialName("posters") val posters: List<MovieImage> = emptyList(),
    @SerialName("backdrops") val backdrops: List<MovieImage> = emptyList(),
    @SerialName("logos") val logos: List<MovieImage> = emptyList()
)

@Serializable
data class MovieImage(
    @SerialName("filePath") val filePath: String,
    @SerialName("width") val width: Int? = null,
    @SerialName("height") val height: Int? = null,
    @SerialName("voteAverage") val voteAverage: Float? = null
)
