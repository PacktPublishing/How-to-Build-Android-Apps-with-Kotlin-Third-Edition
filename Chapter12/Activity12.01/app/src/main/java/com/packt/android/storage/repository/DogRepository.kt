package com.packt.android.storage.repository

import kotlinx.coroutines.flow.Flow

interface DogRepository {

    fun loadDogList(): Flow<Result<List<DogUi>>>

    suspend fun updateNumberOfResults(numberOfResults:Int): Result<Unit>

    suspend fun downloadFile(url: String): Result<Unit>
}