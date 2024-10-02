package com.example.tabnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tabnavigation.ui.theme.TabNavigationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabNavigationTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val tabs = listOf(
        NavigationItem.Home,
        NavigationItem.Shopping,
        NavigationItem.Favorites,
        NavigationItem.Calendar,
        NavigationItem.Bin
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { 5 }
    )

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar( modifier = Modifier.fillMaxWidth(),
                title = { Text("Tab Navigation", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, item ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } },
                        text = { Text(item.title, fontSize = 12.sp) },
                        icon = { Icon(item.icon, contentDescription = item.title) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
            ) { page ->
                when (tabs[page].route) {
                    NavigationItem.Home.route -> HomeScreen()
                    NavigationItem.Shopping.route -> ShoppingCartScreen()
                    NavigationItem.Favorites.route -> FavoritesScreen()
                    NavigationItem.Calendar.route -> CalendarScreen()
                    NavigationItem.Bin.route -> BinScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LateralNavigationScreenPreview() {
    TabNavigationTheme {
        MainScreen()
    }
}