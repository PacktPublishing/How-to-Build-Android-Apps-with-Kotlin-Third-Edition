package com.packt.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PreferenceViewModel(private val preferenceStore: PreferenceStore) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            preferenceStore.textFlow.collect {
                _state.emit(UiState(text = it))
            }
        }
    }

    fun saveText(text: String) {
        viewModelScope.launch {
            preferenceStore.saveText(text)
        }
    }

    data class UiState(
        val text: String = ""
    )
}