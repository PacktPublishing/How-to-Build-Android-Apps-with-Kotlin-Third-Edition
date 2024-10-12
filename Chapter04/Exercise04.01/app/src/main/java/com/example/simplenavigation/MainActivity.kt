package com.example.simplenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplenavigation.ui.theme.SimpleNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNavigationTheme {
                NavigationApp()
            }
        }
    }
}

// Set up NavHost to handle navigation between different routes
@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) { HomeScreen(navController) }
        composable(Routes.Red.route) { ColorScreen(navController, "RED", Color.Red) }
        composable(Routes.Green.route) { ColorScreen(navController, "GREEN", Color.Green) }
        composable(Routes.Blue.route) { ColorScreen(navController, "BLUE", Color.Blue) }
    }
}

@Preview
@Composable
fun Preview_NavigationApp() {
    NavigationApp()
}
