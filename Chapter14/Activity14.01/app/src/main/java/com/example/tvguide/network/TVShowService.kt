package com.example.tvguide.network

import com.example.tvguide.model.TVShow
import com.example.tvguide.model.TVShowResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TVShowService(private val apiKey: String) {
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

    suspend fun getTVShows(): List<TVShow> {
        val response = getTVResponse("${baseUrl}tv/on_the_air")
        return response.results
    }

    private suspend fun getTVResponse(url: String): TVShowResponse {
        return httpClient.get(url) {
            url {
                parameters.append("api_key", apiKey)
            }
        }.body<TVShowResponse>()
    }
}