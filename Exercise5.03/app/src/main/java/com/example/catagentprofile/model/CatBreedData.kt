package com.example.catagentprofile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatBreedData(
    @SerialName("name") val name: String,
    @SerialName("temperament") val temperament: String
)
