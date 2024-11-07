package com.example.navigationdrawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val icon: ImageVector) {
    object Home : NavigationItem("Home", Icons.Default.Home)
    object Shopping : NavigationItem("Cart", Icons.Default.ShoppingCart)
    object Favorites : NavigationItem("Favorites", Icons.Default.Favorite)
    object Calendar : NavigationItem("Calendar", Icons.Default.DateRange)
    object Bin : NavigationItem("Bin", Icons.Default.Delete)
}