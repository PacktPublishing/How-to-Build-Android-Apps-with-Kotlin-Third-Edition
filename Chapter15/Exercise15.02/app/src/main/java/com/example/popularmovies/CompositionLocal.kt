package com.example.popularmovies

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Margin(val margin: Dp)

val LocalMargin = compositionLocalOf {
    Margin(margin = 8.dp)
}