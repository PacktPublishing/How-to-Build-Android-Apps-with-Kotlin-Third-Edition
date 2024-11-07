package com.example.navigationdrawer

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
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Box(contentAlignment = Alignment.Center) {
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

        NavDrawerItem(
            NavigationItem.Home.route,
            scope,
            navController,
            NavigationItem.Home,
            drawerState
        )
        NavDrawerItem(
            NavigationItem.Shopping.route,
            scope,
            navController,
            NavigationItem.Shopping,
            drawerState
        )
        NavDrawerItem(
            NavigationItem.Favorites.route,
            scope,
            navController,
            NavigationItem.Favorites,
            drawerState
        )
        NavDrawerItem(
            NavigationItem.Calendar.route,
            scope,
            navController,
            NavigationItem.Calendar,
            drawerState
        )
        NavDrawerItem(
            NavigationItem.Bin.route,
            scope,
            navController,
            NavigationItem.Bin,
            drawerState
        )
    }
}

@Composable
private fun NavDrawerItem(
    route: String,
    scope: CoroutineScope,
    navController: NavHostController,
    navigationItem: NavigationItem,
    drawerState: DrawerState
) {
    val currentRoute = getCurrentRoute(navController)

    Box(
        Modifier
            .background(if (currentRoute == route) Color.LightGray else Color.White)
            .width(220.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    scope.launch {
                        navController.navigate(navigationItem.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState.close()
                    }
                }

        ) {
            Icon(
                imageVector = navigationItem.icon,
                contentDescription = navigationItem.route,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = navigationItem.route,
                modifier = Modifier.padding(end = 16.dp)
            )
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
        composable(NavigationItem.Home.route) { ContentScreen(NavigationItem.Home.route) }
        composable(NavigationItem.Shopping.route) { ContentScreen(NavigationItem.Shopping.route) }
        composable(NavigationItem.Favorites.route) { ContentScreen(NavigationItem.Favorites.route) }
        composable(NavigationItem.Calendar.route) { ContentScreen(NavigationItem.Calendar.route) }
        composable(NavigationItem.Bin.route) { ContentScreen(NavigationItem.Bin.route) }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    NavigationDrawerTheme {
        MainScreen()
    }
}
