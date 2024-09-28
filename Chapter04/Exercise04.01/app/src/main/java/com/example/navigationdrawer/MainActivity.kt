package com.example.navigationdrawer

import NavigationItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationdrawer.ui.theme.NavigationDrawerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Create a drawer state with the initial value set to Closed
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    // Use ModalNavigationDrawer and ensure the drawer is off-screen initially
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState, scope)
        },
        content = {
            // Scaffold containing the top bar with a button to open the drawer
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Navigation Drawer Example") },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } }
                            ) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            ) { paddingValues ->
                // Main screen content adjusted with padding values from the Scaffold
                NavigationHost(navController, modifier = Modifier.padding(paddingValues))
            }
        }
    )
}

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Shopping,
        NavigationItem.Favorites,
        NavigationItem.Calendar,
        NavigationItem.Bin
    )

    // Observe the current destination to highlight the selected item
    val currentDestination = navController.currentBackStackEntry?.destination?.route

    LazyColumn(
        modifier = Modifier
            .background(Color.White) // Ensure it has padding to avoid accidental visibility
    ) {
        item {
            // Add the image at the top of the drawer
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
        items(items.size) { index ->
            val item = items[index]

            Row(
                modifier = Modifier
                    .background(if (currentDestination == item.route) Color.LightGray else Color.White)
                    .clickable {
                        // Navigate to the selected item and close the drawer
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch { drawerState.close() }
                    }
                    .padding(16.dp)
            ) {
                Icon(item.icon, contentDescription = item.title)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title)
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = NavigationItem.Home.route, modifier = modifier) {
        composable(NavigationItem.Home.route) { HomeScreen() }
        composable(NavigationItem.Shopping.route) { RecentScreen() }
        composable(NavigationItem.Favorites.route) { FavoritesScreen() }
        composable(NavigationItem.Calendar.route) { ArchiveScreen() }
        composable(NavigationItem.Bin.route) { BinScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    NavigationDrawerTheme {
        MainScreen()
    }
}