package com.example.popularmovies

import android.app.Application
import com.example.popularmovies.network.MovieService

class MovieApplication : Application() {
    private val apiKey = "your_api_key_here"

    private val movieService: MovieService by lazy {
        MovieService(apiKey = apiKey)
    }

    val movieRepository: MovieRepository by lazy {
        MovieRepository(movieService = movieService)
    }
}