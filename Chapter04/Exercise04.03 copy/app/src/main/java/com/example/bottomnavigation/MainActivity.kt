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
import androidx.compose.ui.unit.sp
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
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar( modifier = Modifier.fillMaxWidth(),
                title = { Text("My Sports", textAlign = TextAlign.Center, fontSize = 28.sp, modifier = Modifier.fillMaxWidth()) }
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
        NavigationItem.Calendar,
        NavigationItem.Profile,
        NavigationItem.MySports
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.route) },
                label = { Text(item.route) },
                selected = currentRoute == item.route,
                onClick = {navController.navigate(item.route) }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        modifier = modifier
    ) {
        composable(NavigationItem.Home.route) { ContentScreen(NavigationItem.Home.route) }
        composable(NavigationItem.Calendar.route) { ContentScreen(NavigationItem.Calendar.route) }
        composable(NavigationItem.Profile.route) { ContentScreen(NavigationItem.Profile.route) }
        composable(NavigationItem.MySports.route) { SportsScreen(navController) }
        composable(SportsItem.Football.route) { ContentScreen(SportsItem.Football.route) }
        composable(SportsItem.Hockey.route) { ContentScreen(SportsItem.Hockey.route) }
        composable(SportsItem.Baseball.route) { ContentScreen(SportsItem.Baseball.route) }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    BottomNavigationTheme {
        MainScreen()
    }
}