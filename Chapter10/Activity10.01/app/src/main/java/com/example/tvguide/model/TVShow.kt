package com.example.tvguide.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShow(
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName( "first_air_date")
    val firstAirDate: String = "",
    val id: Int = 0,
    val name: String = "",
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_name")
    val originalName: String = "",
    val overview: String = "",
    val popularity: Float = 0f,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("vote_average")
    val voteAverage: Float = 0f,
    @SerialName("vote_count")
    val voteCount: Int = 0
)

@Serializable
data class TVShowResponse(
    val page: Int,
    val results: List<TVShow>
)