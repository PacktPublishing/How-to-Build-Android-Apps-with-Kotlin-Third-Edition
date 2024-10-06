package com.example.bottomnavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Home Screen", fontSize = 28.sp)
    }
}

@Composable
fun ShoppingCartScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Shopping Cart Screen", fontSize = 28.sp)
    }
}

@Composable
fun FavoritesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Favorites Screen", fontSize = 28.sp)
    }
}

@Composable
fun CalendarScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Calendar Screen", fontSize = 28.sp)
    }
}

@Composable
fun BinScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Bin Screen", fontSize = 28.sp)
    }
}