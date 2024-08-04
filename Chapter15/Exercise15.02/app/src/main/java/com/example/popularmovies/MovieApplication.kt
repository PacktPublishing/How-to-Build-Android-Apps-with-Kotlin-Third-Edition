package com.example.popularmovies

import android.app.Application
import com.example.popularmovies.database.MovieDatabase
import com.example.popularmovies.network.MovieService

class MovieApplication : Application() {
    private val apiKey = "your_api_key_here"

    private val movieService: MovieService by lazy {
        MovieService(apiKey = apiKey)
    }

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        val movieDatabase = MovieDatabase.getInstance(applicationContext)

        movieRepository =
            MovieRepository(movieService = movieService, movieDatabase = movieDatabase)
    }
}