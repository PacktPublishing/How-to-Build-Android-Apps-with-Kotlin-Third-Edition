package com.example.tabnavigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ContentScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(name, fontSize = 28.sp)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        "NEWS",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
        , content = { paddingValues ->

            val tabs = listOf(
                "Top Stories",
                "UK News",
                "Politics",
                "World News",
                "Business",
                "Sport",
                "Other"
            )

            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = { 7 }
            )

            val coroutineScope = rememberCoroutineScope()

            Column(modifier = Modifier.padding(paddingValues)) {
                ScrollableTabRow(selectedTabIndex = pagerState.currentPage, edgePadding = 0.dp) {
                    tabs.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } },
                            text = { Text(item, fontSize = 18.sp) },
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                ) { page ->
                    ContentScreen(tabs[page])
                }
            }
        })
}
