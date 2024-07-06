package com.example.catagentprofile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResultData(
    @SerialName("url") val imageUrl: String,
    @SerialName("breeds") val breeds: List<CatBreedData> = emptyList()
)
