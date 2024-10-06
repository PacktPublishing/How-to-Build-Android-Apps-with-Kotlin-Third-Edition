package com.example.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    data object Home : NavigationItem("home", Icons.Default.Home, "Home")
    data object Shopping : NavigationItem("recent", Icons.Default.ShoppingCart, "Cart")
    data object Favorites : NavigationItem("favorites", Icons.Default.Favorite, "Favorites")
    data object Calendar : NavigationItem("archive", Icons.Default.DateRange, "Calendar")
    data object Bin : NavigationItem("bin", Icons.Default.Delete, "Bin")
}