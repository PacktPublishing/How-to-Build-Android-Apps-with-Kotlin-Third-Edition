package com.example.bottomnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.ui.theme.BottomNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavigationTheme {
                BottomNavScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavScreen() {
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

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Shopping,
        NavigationItem.Favorites,
        NavigationItem.Calendar,
        NavigationItem.Bin
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = NavigationItem.Home.route, modifier = modifier) {
        composable(NavigationItem.Home.route) { HomeScreen() }
        composable(NavigationItem.Shopping.route) { ShoppingCartScreen() }
        composable(NavigationItem.Favorites.route) { FavoritesScreen() }
        composable(NavigationItem.Calendar.route) { CalendarScreen() }
        composable(NavigationItem.Bin.route) { BinScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    BottomNavigationTheme {
        BottomNavScreen()
    }
}