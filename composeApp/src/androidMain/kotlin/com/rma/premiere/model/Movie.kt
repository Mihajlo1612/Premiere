package com.rma.premiere.model

data class Movie(
    val title: String,
    val year: String,
    val rating: Double,
    val votes: String,
    val genres: List<String>
)
