package com.example.tvguide

import com.example.tvguide.model.TVShow
import com.example.tvguide.network.TVShowService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TVShowRepository(private val tvShowService: TVShowService) {

    suspend fun getTVShows(): Flow<List<TVShow>> {
        return flow {
            emit(tvShowService.getTVShows())
        }.flowOn(Dispatchers.IO)
    }
}