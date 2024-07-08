package com.example.popularmovies.network

import com.example.popularmovies.model.Movie
import com.example.popularmovies.model.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MovieService(private val apiKey: String) {
    private val baseUrl = "https://api.themoviedb.org/3/"

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getPopularMovies(): List<Movie> {
        val response = getMoviesResponse("${baseUrl}movie/popular")
        return response.results
    }

    private suspend fun getMoviesResponse(url: String): MoviesResponse {
        return httpClient.get(url) {
            url {
                parameters.append("api_key", apiKey)
            }
        }.body<MoviesResponse>()
    }
}