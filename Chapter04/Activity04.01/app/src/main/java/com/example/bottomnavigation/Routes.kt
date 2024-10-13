package com.example.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val icon: ImageVector) {
    object Home : NavigationItem("Home", Icons.Default.Home)
    object Calendar : NavigationItem("Calendar", Icons.Default.DateRange)
    object Profile : NavigationItem("Profile", Icons.Default.Person)
    object MySports : NavigationItem("My Sports", Icons.Default.Star)
}

sealed class SportsItem(val route: String) {
    object Football : SportsItem("Football")
    object Hockey : SportsItem("Hockey")
    object Baseball : SportsItem("Baseball")
}

