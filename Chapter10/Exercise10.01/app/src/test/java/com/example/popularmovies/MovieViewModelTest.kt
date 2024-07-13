package com.example.popularmovies

import com.example.popularmovies.model.Movie
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.Calendar

class MovieViewModelTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var movieRepository: MovieRepository

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun getPopularMovies() {
        val movies = listOf(
            Movie(
                title = "Title",
                releaseDate = Calendar.getInstance().get(Calendar.YEAR).toString()
            )
        )

        val movieViewModel = MovieViewModel(movieRepository, testDispatcher)

        runTest {
            coEvery { movieRepository.getPopularMovies() } returns movies

            movieViewModel.getPopularMovies()
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(movies, movieViewModel.popularMovies.value)
        }
    }
}