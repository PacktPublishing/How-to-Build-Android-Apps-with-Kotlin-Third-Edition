package com.example.popularmovies

import com.example.popularmovies.model.Movie
import com.example.popularmovies.network.MovieService

class MovieRepository(private val movieService: MovieService) {

    suspend fun getPopularMovies(): List<Movie> {
        return movieService.getPopularMovies()
    }
}