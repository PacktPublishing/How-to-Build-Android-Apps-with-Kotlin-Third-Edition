package com.example.weatherapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponseData(
    @SerialName("weather") val weather: List<WeatherData>
)
