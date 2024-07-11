package com.example.tvguide

import android.app.Application
import com.example.tvguide.network.TVShowService

class TVShowApplication : Application() {
    private val apiKey = "your_api_key_here"

    private val tvShowService: TVShowService by lazy {
        TVShowService(apiKey = apiKey)
    }

    val tvShowRepository: TVShowRepository by lazy {
        TVShowRepository(tvShowService = tvShowService)
    }
}