package com.packt.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packt.android.storage.repository.DogRepository
import com.packt.android.storage.repository.DogUi
import com.packt.android.storage.repository.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel(private val dogRepository: DogRepository) : ViewModel() {

    private val _state = MutableStateFlow<Result<List<DogUi>>>(Result.Loading())
    val state: StateFlow<Result<List<DogUi>>> = _state

    init {
        viewModelScope.launch {
            loadDogList()
        }
    }

    fun saveNumberOfResult(numberOfResults: Int) {
        viewModelScope.launch {
            dogRepository.updateNumberOfResults(numberOfResults)
            loadDogList()
        }
    }

    fun downloadDog(url: String) {
        viewModelScope.launch {
            dogRepository.downloadFile(url)
        }
    }

    private suspend fun loadDogList() {
        dogRepository.loadDogList()
            .collect {
                _state.emit(it)
            }
    }
}