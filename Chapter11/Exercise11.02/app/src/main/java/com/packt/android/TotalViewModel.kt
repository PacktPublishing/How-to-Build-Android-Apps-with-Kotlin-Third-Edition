package com.packt.android

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TotalViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    fun incrementResult() {
        val newResult = _state.value.result + 1
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    result = newResult,
                    textColor = if (newResult.mod(2) == 0) Color.Blue else Color.Red
                )
            )
        }
    }

    data class UiState(
        val result: Int = 0,
        val textColor: Color = Color.Blue
    )
}