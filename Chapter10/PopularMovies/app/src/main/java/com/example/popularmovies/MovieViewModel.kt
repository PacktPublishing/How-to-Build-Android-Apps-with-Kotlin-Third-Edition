package com.example.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.popularmovies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieViewModel(val movieRepository: MovieRepository) : ViewModel() {
    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies = _popularMovies.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun getPopularMovies() {

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                MovieViewModel(movieRepository = application.movieRepository)
            }
        }
    }
}