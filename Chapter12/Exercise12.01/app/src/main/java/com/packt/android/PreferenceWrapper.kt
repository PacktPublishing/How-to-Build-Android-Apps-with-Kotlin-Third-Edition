package com.packt.android

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

const val KEY_TEXT = "keyText"

class PreferenceWrapper(private val sharedPreferences: SharedPreferences) {

    private val _textFlow = MutableStateFlow(sharedPreferences.getString(KEY_TEXT, "").orEmpty())
    val textFlow: StateFlow<String> = _textFlow

    suspend fun saveText(text: String) {
        sharedPreferences.edit()
            .putString(KEY_TEXT, text)
            .apply()
        _textFlow.emit(text)
    }
}