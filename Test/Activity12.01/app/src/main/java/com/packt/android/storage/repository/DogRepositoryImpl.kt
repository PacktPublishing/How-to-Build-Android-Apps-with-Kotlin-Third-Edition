package com.packt.android.storage.repository

import com.packt.android.api.DogService
import com.packt.android.storage.filesystem.ProviderFileHandler
import com.packt.android.storage.preference.DownloadPreferences
import com.packt.android.storage.room.DogDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class DogRepositoryImpl(
    private val downloadPreferences: DownloadPreferences,
    private val providerFileHandler: ProviderFileHandler,
    private val dogService: DogService,
    private val dogDao: DogDao,
    private val dogMapper: DogMapper
) : DogRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadDogList(): Flow<Result<List<DogUi>>> {
        return downloadPreferences.resultNumberFlow.flatMapLatest {
            flowOf(dogService.getDogs(it))
        }.map {
            dogMapper.mapServiceToEntity(it)
        }.onEach {
            dogDao.insertDogs(it)
        }.map {
            Result.Success(it.map { dogMapper.mapEntityToUi(it) }) as Result<List<DogUi>>
        }.catch {
            emit(Result.Error())
        }.onStart {
            emit(Result.Loading())
        }
    }

    override suspend fun downloadFile(url: String): Result<Unit> {
        try {
            val body = dogService.downloadFile(url)
            val name = url.substring(url.lastIndexOf("/") + 1)
            providerFileHandler.writeStream(name, body.byteStream())
            return Result.Success(Unit)
        }catch (e: Exception){
            return Result.Error()
        }
    }

    override suspend fun updateNumberOfResults(numberOfResults: Int): Result<Unit> {
        downloadPreferences.saveResultNumber(numberOfResults)
        return Result.Success(Unit)
    }

}