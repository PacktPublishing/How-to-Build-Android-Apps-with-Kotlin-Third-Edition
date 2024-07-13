package com.example.popularmovies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    val id: Int = 0,
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Float = 0f,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Float = 0f,
    @SerialName("vote_count")
    val voteCount: Int = 0
)

@Serializable
data class MoviesResponse(
    val page: Int,
    val results: List<Movie>
)