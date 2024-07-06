package com.example.weatherapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    @SerialName("main") val shortDescription: String,
    @SerialName("description") val longDescription: String,
    @SerialName("icon") val iconId: String
)
