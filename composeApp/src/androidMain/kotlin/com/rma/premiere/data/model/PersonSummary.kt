package com.rma.premiere.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonSummary(
    @SerialName("imdbId") val imdbId: String,
    @SerialName("name") val name: String,
    @SerialName("department") val department: String? = null,
    @SerialName("profilePath") val profilePath: String? = null
)
