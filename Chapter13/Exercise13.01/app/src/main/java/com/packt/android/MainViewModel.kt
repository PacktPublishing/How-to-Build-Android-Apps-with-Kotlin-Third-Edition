package com.packt.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val numberRepository: NumberRepository) : ViewModel() {

    private val _state = MutableStateFlow<Int>(0)
    val state: StateFlow<Int> = _state

    fun generateNextNumber() {
        viewModelScope.launch {
            _state.emit(numberRepository.generateNextNumber())
        }
    }
}