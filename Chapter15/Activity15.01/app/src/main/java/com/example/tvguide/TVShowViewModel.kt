package com.example.tvguide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tvguide.model.TVShow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TVShowViewModel(
    private val tvShowRepository: TVShowRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _tvShows = MutableStateFlow(emptyList<TVShow>())
    val tvShows = _tvShows.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun getTVShows() {
        viewModelScope.launch(dispatcher) {
            tvShowRepository.getTVShows()
                .catch {
                    _error.value = "An error occurred: ${it.message}"
                }
                .collect {
                    _tvShows.value = it
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TVShowApplication)
                TVShowViewModel(tvShowRepository = application.tvShowRepository)
            }
        }
    }
}