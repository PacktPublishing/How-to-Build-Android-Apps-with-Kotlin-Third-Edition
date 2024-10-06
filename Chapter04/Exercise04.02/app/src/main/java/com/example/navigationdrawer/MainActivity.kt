package com.example.navigationdrawer

import NavigationItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentScreenTitle = remember { mutableStateOf("") }

    // Update the screen title based on the current destination
    navController.addOnDestinationChangedListener { _, destination, _ ->
        currentScreenTitle.value = when (destination.route) {
            NavigationItem.Home.route -> "Home"
            NavigationItem.Shopping.route -> "Shopping Cart"
            NavigationItem.Favorites.route -> "Favorites"
            NavigationItem.Calendar.route -> "Calendar"
            NavigationItem.Bin.route -> "Bin"
            else -> "Navigation Drawer Example"
        }
    }

    // Ensure ModalNavigationDrawer and its content fills the available space
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxSize(), // Use fillMaxSize() to prevent size issues
        drawerContent = {
            DrawerContent(navController, drawerState, scope)
        },
        content = {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(currentScreenTitle.value, fontSize = 30.sp) },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxSize() // Ensure Scaffold takes up the full space
            ) { paddingValues ->
                NavigationHost(
                    navController,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
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

    val currentDestination = getCurrentRoute(navController) ?: Color.LightGray


    Column(
        modifier = Modifier.background(Color.White)
    ) {

        val home = NavigationItem.Home
        val shopping = NavigationItem.Shopping
        val favourites = NavigationItem.Favorites
        val calendar = NavigationItem.Calendar
        val bin = NavigationItem.Bin

        Box( contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.width(220.dp),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Logo",

            )
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(Modifier.background(if (currentDestination == "home") Color.LightGray else Color.White).width(220.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            navController.navigate(home.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    }

            ) {
                Icon(
                    imageVector = home.icon,
                    contentDescription = home.title,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = home.title,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Box(Modifier.background(if (currentDestination == "shopping") Color.LightGray else Color.White).width(220.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            navController.navigate(shopping.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    }

            ) {
                Icon(
                    imageVector = shopping.icon,
                    contentDescription = home.title,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = shopping.title,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Box(Modifier.background(if (currentDestination == "favorites") Color.LightGray else Color.White).width(220.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            navController.navigate(favourites.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    }

            ) {
                Icon(
                    imageVector = favourites.icon,
                    contentDescription = favourites.title,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = favourites.title,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Box(Modifier.background(if (currentDestination == "calendar") Color.LightGray else Color.White).width(220.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            navController.navigate(calendar.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    }

            ) {
                Icon(
                    imageVector = calendar.icon,
                    contentDescription = calendar.title,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = calendar.title,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Box(Modifier.background(if (currentDestination == "bin") Color.LightGray else Color.White).width(220.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            navController.navigate(bin.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    }

            ) {
                Icon(
                    imageVector = bin.icon,
                    contentDescription = bin.title,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = bin.title,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    }
}

@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    return navBackStackEntry?.destination?.route
}


@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        modifier = modifier
    ) {
        composable(NavigationItem.Home.route) { HomeScreen() }
        composable(NavigationItem.Shopping.route) { ShoppingCart() }
        composable(NavigationItem.Favorites.route) { FavoritesScreen() }
        composable(NavigationItem.Calendar.route) { CalendarScreen() }
        composable(NavigationItem.Bin.route) { BinScreen() }
    }
}
//

//

//
//    @Preview(showBackground = true)
//    @Composable
//    fun MyAppPreview() {
//        NavigationDrawerTheme {
//            MainScreen()
//        }
//    }
//
//

