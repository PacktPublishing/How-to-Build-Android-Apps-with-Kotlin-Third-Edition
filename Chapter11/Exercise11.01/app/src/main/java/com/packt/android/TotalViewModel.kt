package com.packt.android

import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {

    var result: Int = 0

    fun incrementResult() {
        result++
    }
}