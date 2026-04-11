package com.rma.premiere.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("key") val key: String,
    @SerialName("site") val site: String,
    @SerialName("name") val name: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("official") val official: Boolean = false
)
