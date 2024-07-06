package com.example.popularmovies

import com.example.popularmovies.model.Movie
import com.example.popularmovies.network.MovieService
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Calendar

class MovieRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var movieService: MovieService

    @Test
    fun fetchMovies() {
        val movies = listOf(
            Movie(
                title = "Title",
                releaseDate = Calendar.getInstance().get(Calendar.YEAR).toString()
            )
        )

        val movieRepository = MovieRepository(movieService)

        runBlocking {
            coEvery { movieService.getPopularMovies()  } returns movies
            assertEquals(movies, movieRepository.getPopularMovies())
        }
    }
}