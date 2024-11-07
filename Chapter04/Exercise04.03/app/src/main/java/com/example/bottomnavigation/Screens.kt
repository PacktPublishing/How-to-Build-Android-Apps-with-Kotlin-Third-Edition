package com.example.bottomnavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun ContentScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(name, fontSize = 28.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar( modifier = Modifier.fillMaxWidth(),
                title = { Text("Tab Navigation", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavigationHost(navController, modifier = Modifier.padding(paddingValues))
    }
}
